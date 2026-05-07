package servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Cliente;
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

}
