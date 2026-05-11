package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import models.Entrega;
import services.EntregaServicio;
import utils.JsonUtil;
import utils.LocalDateTimeAdapter;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/entrega" })
public class EntregaServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    private final EntregaServicio entregaServicio = new EntregaServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAutenticacion(req, res)) {
                return;
            }

            Entrega entrega = gson.fromJson(req.getReader(), Entrega.class);
            entregaServicio.registrarNuevaEntrega(entrega);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Entrega registrada exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al registrar la entrega: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAutenticacion(req, res)) {
                return;
            }

            List<Entrega> entregas = entregaServicio.obtenerTodasLasEntregas();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(gson.toJson(entregas));
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al obtener entregas: " + e.getMessage());
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAutenticacion(req, res)) {
                return;
            }

            EstadoEntregaRequest request = gson.fromJson(req.getReader(), EstadoEntregaRequest.class);
            entregaServicio.actualizarEstadoEntrega(request.getId(), request.isAceptado(), request.getMotivoRechazo());
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "message", "Estado de entrega actualizado exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar el estado de la entrega: " + e.getMessage());
        }
    }

    // Clase auxiliar para recibir los datos del PATCH
    public static class EstadoEntregaRequest {
        private int id;
        private boolean aceptado;
        private String motivoRechazo;

        public EstadoEntregaRequest(int id, boolean aceptado, String motivoRechazo) {
            this.id = id;
            this.aceptado = aceptado;
            this.motivoRechazo = motivoRechazo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isAceptado() {
            return aceptado;
        }

        public void setAceptado(boolean aceptado) {
            this.aceptado = aceptado;
        }

        public String getMotivoRechazo() {
            return motivoRechazo;
        }

        public void setMotivoRechazo(String motivoRechazo) {
            this.motivoRechazo = motivoRechazo;
        }
    }

}
