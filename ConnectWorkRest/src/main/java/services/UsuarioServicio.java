package services;

import java.sql.SQLException;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import daos.UsuarioDAO;
import enums.TipoUsuario;
import exceptions.CorreoUsuarioException;
import exceptions.NombreUsuarioException;
import models.Usuario;

public class UsuarioServicio {

    private final UsuarioDAO usuarioDAO;

    public UsuarioServicio() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void crearUsuario(Usuario usuario) throws SQLException, NombreUsuarioException, CorreoUsuarioException {
        if (!usuarioYaExiste(usuario)) {
            crearNuevoUsuario(usuario);
        }
    }

    public boolean esAdministrador(String username) throws SQLException {
        Usuario usuario = usuarioDAO.obtenerUsuario(username);
        return usuario != null && usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR;
    }

    public boolean esCliente(String username) throws SQLException {
        Usuario usuario = usuarioDAO.obtenerUsuario(username);
        return usuario != null && usuario.getTipoUsuario() == TipoUsuario.CLIENTE;
    }

    public boolean esFreelancer(String username) throws SQLException {
        Usuario usuario = usuarioDAO.obtenerUsuario(username);
        return usuario != null && usuario.getTipoUsuario() == TipoUsuario.FREELANCER;
    }

    public Usuario obtenerUsuario(String username) throws SQLException {
        return usuarioDAO.obtenerUsuario(username);
    }

    public List<Usuario> obtenerTodosLosUsuarios() throws SQLException {
        return usuarioDAO.obtenerTodos();
    }

    private boolean usuarioYaExiste(Usuario usuario)
            throws SQLException, NombreUsuarioException, CorreoUsuarioException {
        Usuario usuarioPorNombre = usuarioDAO.obtenerUsuario(usuario.getUsername());
        if (usuarioPorNombre != null) {
            throw new NombreUsuarioException("El nombre de usuario ya está registrado.");
        }

        Usuario usuarioPorEmail = usuarioDAO.obtenerUsuario(usuario.getEmail());
        if (usuarioPorEmail != null) {
            throw new CorreoUsuarioException("El correo electrónico ya está registrado.");
        }

        return false;
    }

    private void crearNuevoUsuario(Usuario usuario) throws SQLException {
        usuario.setPassword(BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt()));
        try {
            usuarioDAO.registrarNuevoUsuario(usuario);
        } catch (SQLException e) {
            throw new SQLException("Error al crear el perfil: " + e.getMessage(), e);
        }
    }
}
