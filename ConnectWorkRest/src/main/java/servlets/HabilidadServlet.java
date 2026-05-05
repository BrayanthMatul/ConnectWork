package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exceptions.NombreRepetidoException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Habilidad;
import models.EstadoRequest;
import services.HabilidadServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = { "/api/habilidad" })
public class HabilidadServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private final HabilidadServicio habilidadServicio = new HabilidadServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }
            Habilidad habilidad = gson.fromJson(req.getReader(), Habilidad.class);
            habilidadServicio.registrarNuevaHabilidad(habilidad);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "mensaje", "Habilidad creada exitosamente");
        } catch (NombreRepetidoException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            JsonUtil.escribirJson(res, "error",
                    "No se pudo registrar la habilidad. Ya existe una habilidad con ese nombre.");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al crear la habilidad: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            List<Habilidad> habilidades = habilidadServicio.obtenerTodasLasHabilidades();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(gson.toJson(habilidades));
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al obtener habilidades: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }
            Habilidad habilidad = gson.fromJson(req.getReader(), Habilidad.class);
            habilidadServicio.actualizarHabilidad(habilidad);
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "mensaje", "Habilidad actualizada exitosamente");
        } catch (NombreRepetidoException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            JsonUtil.escribirJson(res, "error",
                    "No se pudo actualizar la habilidad. Ya existe una habilidad con ese nombre.");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar la habilidad: " + e.getMessage());
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }
            EstadoRequest estadoData = gson.fromJson(req.getReader(), EstadoRequest.class);
            habilidadServicio.actualizarEstadoHabilidad(estadoData.getId(), estadoData.isActivo());
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "mensaje", "Estado de la habilidad actualizado exitosamente");
        } catch (NumberFormatException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.escribirJson(res, "error", "ID de habilidad inválido");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar el estado: " + e.getMessage());
        }
    }
}
