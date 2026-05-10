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
import models.EstadoRequest;
import models.HabilidadSolicitud;
import services.HabilidadSolicitudServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/habilidad-solicitud" })
public class HabilidadSolicitudServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final HabilidadSolicitudServicio habilidadSolicitudServicio = new HabilidadSolicitudServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarFreelancer(req, res)) {
                return;
            }

            HabilidadSolicitud solicitud = gson.fromJson(req.getReader(), HabilidadSolicitud.class);
            habilidadSolicitudServicio.registrarSolicitud(solicitud);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Solicitud de habilidad registrada exitosamente");
        } catch (NombreRepetidoException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            JsonUtil.escribirJson(res, "error",
                    "Una habilidad con este nombre ya existe en la categoría seleccionada. Por favor, elige otro nombre.");
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

            List<HabilidadSolicitud> solicitudes = habilidadSolicitudServicio.obtenerTodasLasSolicitudes();
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
            habilidadSolicitudServicio.actualizarEstadoSolicitud(estadoData);
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "message", "Estado de la solicitud actualizado exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar el estado: " + e.getMessage());
        }
    }

}
