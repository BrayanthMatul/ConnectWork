package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.HistorialPorcentajeComision;
import services.HistorialPorcentajeComisionServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/historial-comision" })
public class HistorialPorcentajeComisionServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                @Override
                public void write(JsonWriter out, LocalDateTime value) throws IOException {
                    if (value == null) {
                        out.nullValue();
                    } else {
                        out.value(value.format(DateTimeFormatter.ISO_DATE_TIME));
                    }
                }

                @Override
                public LocalDateTime read(JsonReader in) throws IOException {
                    String value = in.nextString();
                    return value == null || value.isEmpty() ? null
                            : LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
                }
            })
            .create();
    private final HistorialPorcentajeComisionServicio historialComisionServicio = new HistorialPorcentajeComisionServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }

            HistorialPorcentajeComision historialComision = gson.fromJson(req.getReader(),
                    HistorialPorcentajeComision.class);
            historialComisionServicio.registrarNuevoHistorialComision(historialComision);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Historial de comisión registrado exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al registrar el historial: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {

            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }

            List<HistorialPorcentajeComision> historiales = historialComisionServicio.obtenerTodosLosHistoriales();

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(gson.toJson(historiales));
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al obtener los historiales: " + e.getMessage());
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error interno del servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
