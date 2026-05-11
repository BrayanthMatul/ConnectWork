package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import database.ConexionDB;
import enums.EstadoPropuesta;
import models.Propuesta;

public class PropuestaDAO {

    public void insertar(Propuesta propuesta) throws SQLException {
        String query = "INSERT INTO propuestas (id_proyecto, id_freelancer, monto_ofertado, mensaje, fecha_hora, plazo_entrega, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, propuesta.getIdProyecto());
            ps.setInt(2, propuesta.getIdFreelancer());
            ps.setBigDecimal(3, propuesta.getMontoOfertado());
            ps.setString(4, propuesta.getMensaje());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, propuesta.getPlazoEntrega());
            ps.setString(7, propuesta.getEstado().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al registrar la nueva propuesta: " + e.getMessage(), e);
        }
    }

    public List<Propuesta> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM propuestas";
        List<Propuesta> propuestas = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idProyecto = rs.getInt("id_proyecto");
                int idFreelancer = rs.getInt("id_freelancer");
                java.math.BigDecimal montoOfertado = rs.getBigDecimal("monto_ofertado");
                String mensaje = rs.getString("mensaje");
                Timestamp fechaHoraTS = rs.getTimestamp("fecha_hora");
                LocalDateTime fechaHora = fechaHoraTS != null ? fechaHoraTS.toLocalDateTime() : null;
                int plazoEntrega = rs.getInt("plazo_entrega");
                String estado = rs.getString("estado");
                EstadoPropuesta estadoPropuesta = EstadoPropuesta.valueOf(estado);
                propuestas.add(new Propuesta(id, idProyecto, idFreelancer, montoOfertado, mensaje, plazoEntrega,
                        estadoPropuesta, fechaHora));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las propuestas: " + e.getMessage(), e);
        }
        return propuestas;
    }

    public boolean actualizarEstado(int id, EstadoPropuesta nuevoEstado) throws SQLException {
        String estadoString = mapearEstado(nuevoEstado);
        String query = "UPDATE propuestas SET estado = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, estadoString);
            ps.setInt(2, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado de la propuesta: " + e.getMessage(), e);
        }
    }

    private String mapearEstado(EstadoPropuesta estado) {
        switch (estado) {
            case PENDIENTE:
                return "PENDIENTE";
            case ACEPTADA:
                return "ACEPTADA";
            case RECHAZADA:
                return "RECHAZADA";
            default:
                return "DESCONOCIDO";
        }
    }

}
