package daos;

import database.ConexionDB;
import models.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public void insertarCategoria(Categoria categoria) throws SQLException {
        String query = "INSERT INTO categorias (nombre, descripcion, activo) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setBoolean(3, categoria.isActivo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("No se pudo obtener el ID de la nueva categoría.");
        }
    }

    public Categoria obtenerPorNombre(String nombre) throws SQLException {
        String query = "SELECT * FROM categorias WHERE nombre = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String descripcion = rs.getString("descripcion");
                    boolean activo = rs.getBoolean("activo");
                    return new Categoria(id, nombre, descripcion, activo);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener la categoría por nombre: " + e.getMessage());
        }
        return null;
    }

    public List<Categoria> obtenerTodas() throws SQLException {
        String query = "SELECT * FROM categorias";
        List<Categoria> categorias = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                boolean activo = rs.getBoolean("activo");
                categorias.add(new Categoria(id, nombre, descripcion, activo));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las categorías: " + e.getMessage());
        }
        return categorias;
    }

    public void actualizarEstado(int id, boolean activo) throws SQLException {
        String query = "UPDATE categorias SET activo = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setBoolean(1, activo);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado de la categoría: " + e.getMessage());
        }
    }

    public void actualizarCategoria(Categoria categoria) throws SQLException {
        String query = "UPDATE categorias SET nombre = ?, descripcion = ?, activo = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setBoolean(3, categoria.isActivo());
            ps.setInt(4, categoria.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar la categoría: " + e.getMessage());
        }
    }
}