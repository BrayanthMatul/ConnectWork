package daos;

import database.ConexionDB;
import models.HistorialPorcentajeComision;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistorialPorcentajeComisionDAO {

    public void insertar(HistorialPorcentajeComision historialComision) throws SQLException {
        LocalDateTime fechaHoraFin = LocalDateTime.now();
        agregarFechaFinUltimoHistorial(fechaHoraFin);

        String query = "INSERT INTO historial_porcentajes_comisiones (fecha_hora_inicio, porcentaje_comision) VALUES (?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(historialComision.getFechaHoraInicio()));
            ps.setBigDecimal(2, historialComision.getPorcentajeComision());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar el historial de porcentaje de comisión: " + e.getMessage(), e);
        }
    }

    public List<HistorialPorcentajeComision> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM historial_porcentajes_comisiones";
        List<HistorialPorcentajeComision> historiales = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDateTime fechaHoraInicio = rs.getTimestamp("fecha_hora_inicio").toLocalDateTime();
                Timestamp fechaHoraFinTs = rs.getTimestamp("fecha_hora_fin");
                LocalDateTime fechaHoraFin = fechaHoraFinTs != null ? fechaHoraFinTs.toLocalDateTime() : null;
                BigDecimal porcentajeComision = rs.getBigDecimal("porcentaje_comision");
                historiales.add(new HistorialPorcentajeComision(id, fechaHoraInicio, fechaHoraFin, porcentajeComision));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el historial de porcentajes de comisión: " + e.getMessage(), e);
        }
        return historiales;
    }

    private void agregarFechaFinUltimoHistorial(LocalDateTime fechaHoraFin) throws SQLException {
        String query = "UPDATE historial_porcentajes_comisiones SET fecha_hora_fin = ? WHERE fecha_hora_fin IS NULL";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(fechaHoraFin));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el último historial de comisión: " + e.getMessage(), e);
        }
    }

}
