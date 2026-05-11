package servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Contrato;
import models.ContratoDTO;
import models.EstadoContratoRequest;
import services.ContratoServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/contrato" })
public class ContratoServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder().create();
    private final ContratoServicio contratoServicio = new ContratoServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAutenticacion(req, res)) {
                return;
            }

            ContratoDTO contratoDTO = gson.fromJson(req.getReader(), ContratoDTO.class);
            Contrato contrato = new Contrato(0, contratoDTO.getIdPropuesta(), null,
                    contratoDTO.getMonto(), contratoDTO.getEstado(), contratoDTO.getCalificacion());

            contratoServicio.registrarNuevoContrato(contrato);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Contrato registrado exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al registrar el contrato: " + e.getMessage());
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
            gson.toJson(contratoServicio.obtenerTodosLosContratos(), res.getWriter());
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al obtener los contratos: " + e.getMessage());
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAutenticacion(req, res)) {
                return;
            }

            EstadoContratoRequest request = gson.fromJson(req.getReader(), EstadoContratoRequest.class);
            contratoServicio.actualizarEstado(request);
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "message", "Estado del contrato actualizado exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar el estado del contrato: " + e.getMessage());
        }
    }

}
