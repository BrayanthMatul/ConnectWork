package services;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import daos.PerfilDAO;
import daos.UsuarioDAO;
import exceptions.CorreoUsuarioException;
import exceptions.CuiUsuarioException;
import exceptions.NombreUsuarioException;
import models.Perfil;
import models.Usuario;

public class PerfilServicio {

    public void crearPerfil(Perfil perfil)
            throws NombreUsuarioException, CorreoUsuarioException, CuiUsuarioException, SQLException {
        if (!perfilYaExiste(perfil)) {
            crearUsuarioYPerfil(perfil);
        }
    }

    private boolean perfilYaExiste(Perfil perfil)
            throws SQLException, NombreUsuarioException, CorreoUsuarioException, CuiUsuarioException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuarioPorNombre = usuarioDAO.obtenerUsuario(perfil.getUsuario().getUsername());
        if (usuarioPorNombre != null) {
            throw new NombreUsuarioException("El nombre de usuario ya está registrado.");
        }

        Usuario usuarioPorEmail = usuarioDAO.obtenerUsuario(perfil.getUsuario().getEmail());
        if (usuarioPorEmail != null) {
            throw new CorreoUsuarioException("El correo electrónico ya está registrado.");
        }

        PerfilDAO perfilDAO = new PerfilDAO();
        Perfil perfilExistente = perfilDAO.obtenerPerfilPorCui(perfil.getCui());
        if (perfilExistente != null) {
            throw new CuiUsuarioException("El perfil con el CUI proporcionado ya existe.");
        }

        return false;
    }

    private void crearUsuarioYPerfil(Perfil nuevoPerfil) throws SQLException {

        Usuario usuarioNuevo = nuevoPerfil.getUsuario();

        // Encriptar la contraseña antes de guardar el usuario
        usuarioNuevo.setPassword(BCrypt.hashpw(usuarioNuevo.getPassword(), BCrypt.gensalt()));

        try {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            int idUsuario = usuarioDAO.registrarNuevoUsuario(usuarioNuevo);

            nuevoPerfil.setIdPerfil(idUsuario);
            nuevoPerfil.setSaldo(new BigDecimal(0.00));

            PerfilDAO perfilDAO = new PerfilDAO();
            perfilDAO.registrarNuevoPerfil(nuevoPerfil);

        } catch (SQLException e) {
            throw new SQLException("Error al crear el perfil: " + e.getMessage(), e);
        }
    }
}
