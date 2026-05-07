package daos;

import database.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.Cliente;

public class ClienteDAO {

    public void registrarNuevoCliente(Cliente clienteNuevo) throws SQLException {
        String query = "INSERT INTO clientes (id_cliente, descripcion, sector, sitio_web) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, clienteNuevo.getIdCliente());
            ps.setString(2, clienteNuevo.getDescripcion());
            ps.setString(3, clienteNuevo.getSector());
            ps.setString(4, clienteNuevo.getSitioWeb());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo cliente: " + e.getMessage(), e);
        }

        PerfilDAO perfilDAO = new PerfilDAO();
        perfilDAO.marcarPerfilComoCompleto(clienteNuevo.getIdCliente());
    }

}
