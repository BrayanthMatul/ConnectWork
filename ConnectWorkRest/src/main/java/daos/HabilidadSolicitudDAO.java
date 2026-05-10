package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConexionDB;
import models.HabilidadSolicitud;

public class HabilidadSolicitudDAO {

    public void insertar(HabilidadSolicitud habilidadSolicitud) throws SQLException {
        String query = "INSERT INTO habilidad_solicitudes (nombre, descripcion, id_categoria, id_freelancer, aceptada, revisada) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, habilidadSolicitud.getNombre());
            ps.setString(2, habilidadSolicitud.getDescripcion());
            ps.setInt(3, habilidadSolicitud.getIdCategoria());
            ps.setInt(4, habilidadSolicitud.getIdFreelancer());
            ps.setBoolean(5, habilidadSolicitud.isAceptada());
            ps.setBoolean(6, habilidadSolicitud.isRevisada());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al registrar la solicitud de nueva habilidad: " + e.getMessage(), e);
        }
    }

    public List<HabilidadSolicitud> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM habilidad_solicitudes";
        List<HabilidadSolicitud> habilidadSolicitudes = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                int idCategoria = rs.getInt("id_categoria");
                int idFreelancer = rs.getInt("id_freelancer");
                boolean aceptada = rs.getBoolean("aceptada");
                boolean revisada = rs.getBoolean("revisada");
                habilidadSolicitudes.add(
                        new HabilidadSolicitud(id, nombre, descripcion, idCategoria, idFreelancer, aceptada, revisada));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las solicitudes de habilidades: " + e.getMessage(), e);
        }
        return habilidadSolicitudes;
    }

    public void actualizarEstadoSolicitud(int idSolicitud, boolean aceptada) throws SQLException {
        String query = "UPDATE habilidad_solicitudes SET aceptada = ? WHERE id = ?";
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
        String query = "UPDATE habilidad_solicitudes SET revisada = ? WHERE id = ?";
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
