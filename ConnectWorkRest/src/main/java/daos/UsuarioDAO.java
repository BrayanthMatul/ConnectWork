package daos;


import database.ConexionDB;
import enums.TipoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Usuario;

public class UsuarioDAO {

    public Usuario obtenerUsuario(String nombreOrEmail) {
        String query = "SELECT * FROM usuarios WHERE username = ? OR email = ? LIMIT 1";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nombreOrEmail);
            ps.setString(2, nombreOrEmail);
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
            throw new RuntimeException("Error al obtener el usuario: " + e.getMessage(), e);
        }
        return null;
    }

    private TipoUsuario asignarUsuario(String tipo){
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
