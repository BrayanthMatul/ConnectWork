package servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.EstadoPropuestaRequest;
import models.Propuesta;
import models.PropuestaDTO;
import services.PropuestaServicio;
import utils.JsonUtil;
import utils.LocalDateTimeAdapter;
import utils.PermissionUtil;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = { "/api/propuesta" })
public class PropuestaServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    private final PropuestaServicio propuestaServicio = new PropuestaServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarFreelancer(req, res)) {
                return;
            }

            PropuestaDTO propuestaDTO = gson.fromJson(req.getReader(), PropuestaDTO.class);
            Propuesta propuesta = new Propuesta(0, propuestaDTO.getIdProyecto(), propuestaDTO.getIdFreelancer(),
                    propuestaDTO.getMontoOfertado(), propuestaDTO.getMensaje(), propuestaDTO.getPlazoEntrega(),
                    propuestaDTO.getEstado());

            propuestaServicio.registrarNuevaPropuesta(propuesta);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Propuesta registrada exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al registrar la propuesta: " + e.getMessage());
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
            gson.toJson(propuestaServicio.obtenerTodos(), res.getWriter());
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al obtener las propuestas: " + e.getMessage());
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAutenticacion(req, res)) {
                return;
            }

            EstadoPropuestaRequest request = gson.fromJson(req.getReader(), EstadoPropuestaRequest.class);
            boolean actualizado = propuestaServicio.actualizarEstado(request);

            if (actualizado) {
                res.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.escribirJson(res, "message", "Estado de la propuesta actualizado exitosamente");
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.escribirJson(res, "error", "Propuesta no encontrada");
            }
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar el estado de la propuesta: " + e.getMessage());
        }
    }

}
