package services;

import java.sql.SQLException;

import daos.ClienteDAO;
import models.Cliente;

public class ClienteServicio {

    private final ClienteDAO clienteDAO;

    public ClienteServicio() {
        this.clienteDAO = new ClienteDAO();
    }

    public void registrarNuevoCliente(Cliente cliente) throws SQLException {
        try {
            clienteDAO.registrarNuevoCliente(cliente);
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo cliente: " + e.getMessage(), e);
        }
    }
}
