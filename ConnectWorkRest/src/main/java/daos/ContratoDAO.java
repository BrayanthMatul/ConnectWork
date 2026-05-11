package daos;

import database.ConexionDB;
import models.Contrato;
import enums.EstadoContrato;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {

    public void insertar(Contrato contrato) throws SQLException {
        String query = "INSERT INTO contratos (id_propuesta, fecha_inicio, monto, estado, calificacion) VALUES (?, NOW(), ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, contrato.getIdPropuesta());
            ps.setBigDecimal(2, contrato.getMonto());
            ps.setString(3, contrato.getEstado().name());
            ps.setObject(4, contrato.getCalificacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar el contrato: " + e.getMessage(), e);
        }
    }

    public List<Contrato> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM contratos";
        List<Contrato> contratos = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                contratos.add(mapearContrato(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los contratos: " + e.getMessage(), e);
        }
        return contratos;
    }

    public void actualizarEstado(int id, EstadoContrato nuevoEstado) throws SQLException {
        String query = "UPDATE contratos SET estado = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado del contrato: " + e.getMessage(), e);
        }
    }

    private Contrato mapearContrato(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idPropuesta = rs.getInt("id_propuesta");
        Date fechaInicio = rs.getDate("fecha_inicio");
        java.math.BigDecimal monto = rs.getBigDecimal("monto");
        String estado = rs.getString("estado");
        EstadoContrato estadoContrato = EstadoContrato.valueOf(estado);
        Double calificacion = rs.getObject("calificacion") != null ? rs.getDouble("calificacion") : null;
        return new Contrato(id, idPropuesta, fechaInicio, monto, estadoContrato, calificacion);
    }
}
