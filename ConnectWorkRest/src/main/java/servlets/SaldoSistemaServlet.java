package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.SaldoSistema;
import services.SaldoSistemaServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/saldo" })
public class SaldoSistemaServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    private final SaldoSistemaServicio saldoSistemaServicio = new SaldoSistemaServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            SaldoSistema saldoSistema = gson.fromJson(req.getReader(), SaldoSistema.class);
            saldoSistemaServicio.registrarNuevoSaldoSistema(saldoSistema);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Saldo del sistema registrado exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al registrar el saldo: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {

            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }

            List<SaldoSistema> registros = saldoSistemaServicio.obtenerTodosLosRegistros();
            BigDecimal totalSaldo = saldoSistemaServicio.obtenerTotalSaldo();

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("registros", registros);
            respuesta.put("totalSaldo", totalSaldo);

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(gson.toJson(respuesta));
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al obtener los saldos: " + e.getMessage());
        }
    }

}
