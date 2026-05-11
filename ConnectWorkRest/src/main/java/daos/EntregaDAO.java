package daos;

import database.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import models.Entrega;

public class EntregaDAO {

    public void insertar(Entrega entrega) throws SQLException {
        String query = "INSERT INTO entregas (id_contrato, descripcion, archivo_url, fecha_hora, aceptado, revisado, motivo_rechazo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, entrega.getIdContrato());
            ps.setString(2, entrega.getDescripcion());
            ps.setString(3, entrega.getArchivoUrl());
            // Establecer fecha actual si no viene del frontend
            LocalDateTime fechaHora = entrega.getFechaHora() != null ? entrega.getFechaHora() : LocalDateTime.now();
            ps.setTimestamp(4, Timestamp.valueOf(fechaHora));
            ps.setBoolean(5, entrega.isAceptado());
            ps.setBoolean(6, false); // revisado siempre inicia como false
            ps.setString(7, entrega.getMotivoRechazo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar la entrega: " + e.getMessage(), e);
        }
    }

    public List<Entrega> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM entregas";
        List<Entrega> entregas = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idContrato = rs.getInt("id_contrato");
                String descripcion = rs.getString("descripcion");
                String archivoUrl = rs.getString("archivo_url");
                Timestamp timestamp = rs.getTimestamp("fecha_hora");
                LocalDateTime fechaHora = timestamp != null ? timestamp.toLocalDateTime() : null;
                boolean aceptado = rs.getBoolean("aceptado");
                boolean revisado = rs.getBoolean("revisado");
                String motivoRechazo = rs.getString("motivo_rechazo");

                Entrega entrega = new Entrega(id, idContrato, descripcion, archivoUrl, fechaHora, aceptado, revisado,
                        motivoRechazo);
                entregas.add(entrega);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las entregas: " + e.getMessage(), e);
        }
        return entregas;
    }

    public void actualizarEstado(int idEntrega, boolean aceptado, String motivoRechazo) throws SQLException {
        String query = "UPDATE entregas SET aceptado = ?, revisado = true, motivo_rechazo = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setBoolean(1, aceptado);
            ps.setString(2, motivoRechazo);
            ps.setInt(3, idEntrega);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado de la entrega: " + e.getMessage(), e);
        }
    }

}
