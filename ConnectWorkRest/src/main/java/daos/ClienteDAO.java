package daos;

import database.ConexionDB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.Cliente;
import models.Recarga;

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

    public void agregarSaldoCliente(int idCliente, BigDecimal monto) throws SQLException {
        PerfilDAO perfilDAO = new PerfilDAO();
        perfilDAO.agregarSaldo(idCliente, monto);
        agregarRegistroRecarga(idCliente, monto);
    }

    private void agregarRegistroRecarga(int idCliente, BigDecimal monto) throws SQLException {
        RecargaDAO recargaDAO = new RecargaDAO();
        Recarga recarga = new Recarga(0, idCliente, monto, java.time.LocalDateTime.now());
        recargaDAO.insertarRecarga(recarga);
    }

}
