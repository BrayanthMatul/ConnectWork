package daos;

import database.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import models.Recarga;

public class RecargaDAO {

    public void insertarRecarga(Recarga recarga) throws SQLException {
        String query = "INSERT INTO recargas (id_cliente, monto, fecha_hora) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, recarga.getIdCliente());
            ps.setBigDecimal(2, recarga.getMonto());
            ps.setObject(3, recarga.getFechaHora());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar la recarga: " + e.getMessage(), e);
        }
    }

    public List<Recarga> obtenerTodas() throws SQLException {
        String query = "SELECT * FROM recargas";
        List<Recarga> recargas = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idCliente = rs.getInt("id_cliente");
                java.math.BigDecimal monto = rs.getBigDecimal("monto");
                LocalDateTime fechaHora = rs.getObject("fecha_hora", LocalDateTime.class);
                recargas.add(new Recarga(id, idCliente, monto, fechaHora));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las recargas: " + e.getMessage(), e);
        }
        return recargas;
    }

}
