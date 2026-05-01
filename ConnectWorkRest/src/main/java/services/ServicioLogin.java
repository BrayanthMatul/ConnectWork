package services;

import daos.UsuarioDAO;
import models.Usuario;
import models.LoginResponse;

import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;
import utils.JwtUtil;

public class ServicioLogin {

	private final UsuarioDAO usuarioDAO;

	public ServicioLogin() {
		this.usuarioDAO = new UsuarioDAO();
	}

	public LoginResponse loginConToken(String nombreOrEmail, String password) throws SQLException {
		Usuario usuario = usuarioDAO.obtenerUsuario(nombreOrEmail);
		if (usuario != null) {
			boolean match = BCrypt.checkpw(password, usuario.getPassword());
			if (match) {
				String token = JwtUtil.generarToken(usuario.getUsername());
				usuario.setPassword("");
				return new LoginResponse(token, usuario);
			}
		}
		return null;
	}

}
