package servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exceptions.NombreRepetidoException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.EstadoProyectoRequest;
import models.Proyecto;
import services.ProyectoServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/proyecto" })
public class ProyectoServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private final ProyectoServicio proyectoServicio = new ProyectoServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarCliente(req, res)) {
                return;
            }

            Proyecto proyecto = gson.fromJson(req.getReader(), Proyecto.class);
            proyectoServicio.registrarNuevoProyecto(proyecto);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Proyecto registrado exitosamente");
        } catch (NombreRepetidoException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            JsonUtil.escribirJson(res, "error", e.getMessage());
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al registrar el proyecto: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAutenticacion(req, res)) {
                return;
            }

            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json");
            gson.toJson(proyectoServicio.obtenerTodosLosProyectos(), res.getWriter());
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al obtener los proyectos: " + e.getMessage());
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAutenticacion(req, res)) {
                return;
            }

            EstadoProyectoRequest request = gson.fromJson(req.getReader(), EstadoProyectoRequest.class);
            boolean actualizado = proyectoServicio.actualizarEstado(request);

            if (actualizado) {
                res.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.escribirJson(res, "message", "Estado del proyecto actualizado exitosamente");
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.escribirJson(res, "error", "Proyecto no encontrado");
            }
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar el estado del proyecto: " + e.getMessage());
        }
    }

}
