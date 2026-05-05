package daos;

import database.ConexionDB;
import models.Habilidad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabilidadDAO {

    public void insertar(Habilidad habilidad) throws SQLException {
        String query = "INSERT INTO habilidades (id_categoria, nombre, descripcion) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, habilidad.getIdCategoria());
            ps.setString(2, habilidad.getNombre());
            ps.setString(3, habilidad.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar la habilidad: " + e.getMessage(), e);
        }
    }

    public Habilidad obtenerPorNombre(String nombre) throws SQLException {
        String query = "SELECT * FROM habilidades WHERE nombre = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearHabilidad(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener la habilidad por nombre: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Habilidad> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM habilidades";
        List<Habilidad> habilidades = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                habilidades.add(mapearHabilidad(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las habilidades: " + e.getMessage(), e);
        }
        return habilidades;
    }

    public void actualizarEstado(int id, boolean activo) throws SQLException {
        String query = "UPDATE habilidades SET activo = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setBoolean(1, activo);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado de la habilidad: " + e.getMessage(), e);
        }
    }

    public void actualizar(Habilidad habilidad) throws SQLException {
        String query = "UPDATE habilidades SET nombre = ?, descripcion = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, habilidad.getNombre());
            ps.setString(2, habilidad.getDescripcion());
            ps.setInt(3, habilidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar la habilidad: " + e.getMessage(), e);
        }
    }

    private Habilidad mapearHabilidad(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idCategoria = rs.getInt("id_categoria");
        String nombre = rs.getString("nombre");
        String descripcion = rs.getString("descripcion");
        boolean activo = rs.getBoolean("activo");
        return new Habilidad(id, idCategoria, nombre, descripcion, activo);
    }
}
