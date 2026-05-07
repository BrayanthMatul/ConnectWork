package daos;

import database.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.CategoriaSolicitud;

public class CategoriaSolicitudDAO {

    public void insertarCategoriaSolicitud(CategoriaSolicitud solicitud) throws SQLException {
        String query = "INSERT INTO categoria_solicitudes (nombre, descripcion, id_cliente, aceptada, revisada) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, solicitud.getNombre());
            ps.setString(2, solicitud.getDescripcion());
            ps.setInt(3, solicitud.getIdCliente());
            ps.setBoolean(4, solicitud.isAceptada());
            ps.setBoolean(5, solicitud.isRevisada());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar la solicitud: " + e.getMessage(), e);
        }
    }

    public List<CategoriaSolicitud> obtenerTodas() throws SQLException {
        String query = "SELECT * FROM categoria_solicitudes";
        List<CategoriaSolicitud> solicitudes = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                boolean aceptada = rs.getBoolean("aceptada");
                boolean revisada = rs.getBoolean("revisada");
                int idCliente = rs.getInt("id_cliente");
                solicitudes.add(new CategoriaSolicitud(id, nombre, descripcion, aceptada, revisada, idCliente));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las solicitudes: " + e.getMessage(), e);
        }
        return solicitudes;
    }

    public void actualizarEstadoSolicitud(int idSolicitud, boolean aceptada) throws SQLException {
        String query = "UPDATE categoria_solicitudes SET aceptada = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setBoolean(1, aceptada);
            ps.setInt(2, idSolicitud);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado de la solicitud: " + e.getMessage(), e);
        }

        // Marcar la solicitud como revisada
        actualizarRevisadaSolicitud(idSolicitud, true);
    }

    private void actualizarRevisadaSolicitud(int idSolicitud, boolean revisada) throws SQLException {
        String query = "UPDATE categoria_solicitudes SET revisada = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setBoolean(1, revisada);
            ps.setInt(2, idSolicitud);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado de revisión de la solicitud: " + e.getMessage(), e);
        }
    }

}
