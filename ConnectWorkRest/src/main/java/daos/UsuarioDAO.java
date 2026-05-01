package daos;

import database.ConexionDB;
import enums.TipoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.Usuario;

public class UsuarioDAO {

    public int registrarNuevoUsuario(Usuario usuarioNuevo) throws SQLException {
        String query = "INSERT INTO usuarios (username, password, nombre_completo, email, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuarioNuevo.getUsername());
            ps.setString(2, usuarioNuevo.getPassword());
            ps.setString(3, usuarioNuevo.getNombreCompleto());
            ps.setString(4, usuarioNuevo.getEmail());
            ps.setString(5, usuarioNuevo.getTipoUsuario().name());
            ps.executeUpdate();

            // Obtener el ID generado del nuevo usuario
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new SQLException("Error al obtener el ID del nuevo usuario: ");
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo usuario: " + e.getMessage(), e);
        }
    }

    public Usuario obtenerUsuario(String usernameOrEmail) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE username = ? OR email = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String nombreCompleto = rs.getString("nombre_completo");
                    String email = rs.getString("email");
                    String tipo = rs.getString("tipo_usuario");
                    TipoUsuario tipoUsuario = asignarUsuario(tipo);
                    return new Usuario(id, username, password, nombreCompleto, email, tipoUsuario);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el usuario: " + e.getMessage(), e);
        }
        return null;
    }

    private TipoUsuario asignarUsuario(String tipo) {
        switch (tipo.toLowerCase()) {
            case "administrador":
                return TipoUsuario.ADMINISTRADOR;
            case "cliente":
                return TipoUsuario.CLIENTE;
            case "freelancer":
                return TipoUsuario.FREELANCER;
            default:
                return null;
        }
    }

}
