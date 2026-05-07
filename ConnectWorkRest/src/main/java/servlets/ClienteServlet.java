package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Cliente;
import models.SaldoRequest;
import services.ClienteServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/cliente" })
public class ClienteServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final ClienteServicio clienteServicio = new ClienteServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Verificar que sea cliente el que está haciendo la petición
            if (!PermissionUtil.verificarCliente(req, res)) {
                return;
            }

            Cliente cliente = gson.fromJson(req.getReader(), Cliente.class);
            clienteServicio.registrarNuevoCliente(cliente);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Datos iniciales registrados exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al registrar el cliente: " + e.getMessage());
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Verificar que sea cliente el que está haciendo la petición
            if (!PermissionUtil.verificarCliente(req, res)) {
                return;
            }

            SaldoRequest saldoRequest = gson.fromJson(req.getReader(), SaldoRequest.class);
            int idCliente = saldoRequest.getId();
            double monto = saldoRequest.getMonto();
            clienteServicio.agregarSaldoCliente(idCliente, BigDecimal.valueOf(monto));
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "message", "Saldo recargado exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al recargar el saldo: " + e.getMessage());
        } catch (NumberFormatException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.escribirJson(res, "error", "Parámetros inválidos: " + e.getMessage());
        }
    }

}
