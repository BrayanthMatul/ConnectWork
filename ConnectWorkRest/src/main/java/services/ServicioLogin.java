package services;


import daos.UsuarioDAO;
import models.Usuario;
import models.LoginResponse;
import org.mindrot.jbcrypt.BCrypt;
import utils.JwtUtil;

public class ServicioLogin {

	private final UsuarioDAO usuarioDAO;

	public ServicioLogin() {
		this.usuarioDAO = new UsuarioDAO();
	}



	public LoginResponse loginConToken(String nombreOrEmail, String password) {
		Usuario usuario = usuarioDAO.obtenerUsuario(nombreOrEmail);
		if (usuario != null) {
			System.out.println("DEBUG LOGIN:");
			System.out.println("Username recibido: " + nombreOrEmail);
			System.out.println("Password recibido: " + password);
			System.out.println("Hash almacenado: " + usuario.getPassword());
			boolean match = BCrypt.checkpw(password, usuario.getPassword());
			System.out.println("¿Coincide bcrypt?: " + match);
			if (match) {
				String token = JwtUtil.generarToken(usuario.getUsername());
				usuario.setPassword("");
				return new LoginResponse(token, usuario);
			}
		}
		return null;
	}

}
