package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import exceptions.NombreRepetidoException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.CategoriaSolicitud;
import models.EstadoRequest;
import services.CategoriaSolicitudServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/categoria-solicitud" })
public class CategoriaSolicitudServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final CategoriaSolicitudServicio categoriaSolicitudServicio = new CategoriaSolicitudServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarCliente(req, res)) {
                return;
            }

            CategoriaSolicitud solicitud = gson.fromJson(req.getReader(), CategoriaSolicitud.class);
            categoriaSolicitudServicio.registrarSolicitud(solicitud);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Solicitud de categoría registrada exitosamente");
        } catch (NombreRepetidoException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            JsonUtil.escribirJson(res, "error", "El nombre de la categoría ya existe. Por favor, elige otro nombre.");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al registrar la solicitud: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Verificar que sea administrador
            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }

            List<CategoriaSolicitud> solicitudes = categoriaSolicitudServicio.obtenerTodasLasSolicitudes();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(gson.toJson(solicitudes));
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al obtener solicitudes: " + e.getMessage());
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Verificar que sea administrador
            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }

            EstadoRequest estadoData = gson.fromJson(req.getReader(), EstadoRequest.class);
            categoriaSolicitudServicio.actualizarEstadoSolicitud(estadoData);
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "message", "Estado de la solicitud actualizado exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar el estado: " + e.getMessage());
        }
    }

}
