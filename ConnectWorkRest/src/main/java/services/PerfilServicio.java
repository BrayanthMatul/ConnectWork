package services;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import daos.PerfilDAO;
import daos.UsuarioDAO;
import exceptions.CorreoUsuarioException;
import exceptions.CuiUsuarioException;
import exceptions.NombreUsuarioException;
import models.Perfil;
import models.Usuario;

public class PerfilServicio {

    private final UsuarioDAO usuarioDAO;
    private final PerfilDAO perfilDAO;

    public PerfilServicio() {
        this.usuarioDAO = new UsuarioDAO();
        this.perfilDAO = new PerfilDAO();
    }

    public void crearPerfil(Perfil perfil)
            throws NombreUsuarioException, CorreoUsuarioException, CuiUsuarioException, SQLException {
        if (!perfilYaExiste(perfil)) {
            crearUsuarioYPerfil(perfil);
        }
    }

    private boolean perfilYaExiste(Perfil perfil)
            throws SQLException, NombreUsuarioException, CorreoUsuarioException, CuiUsuarioException {
        Usuario usuarioPorNombre = usuarioDAO.obtenerUsuario(perfil.getUsuario().getUsername());
        if (usuarioPorNombre != null) {
            throw new NombreUsuarioException("El nombre de usuario ya está registrado.");
        }

        Usuario usuarioPorEmail = usuarioDAO.obtenerUsuario(perfil.getUsuario().getEmail());
        if (usuarioPorEmail != null) {
            throw new CorreoUsuarioException("El correo electrónico ya está registrado.");
        }

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

            int idUsuario = usuarioDAO.registrarNuevoUsuario(usuarioNuevo);

            nuevoPerfil.setIdPerfil(idUsuario);
            nuevoPerfil.setSaldo(new BigDecimal(0.00));

            perfilDAO.registrarNuevoPerfil(nuevoPerfil);

        } catch (SQLException e) {
            throw new SQLException("Error al crear el perfil: " + e.getMessage(), e);
        }
    }

    public List<Perfil> obtenerTodosLosPerfiles() throws SQLException {
        List<Perfil> perfiles = perfilDAO.obtenerTodos();
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();

        // Mapear cada perfil con su usuario correspondiente
        for (Perfil perfil : perfiles) {
            for (Usuario usuario : usuarios) {
                if (perfil.getIdPerfil() == usuario.getId()) {
                    perfil.setUsuario(usuario);
                    break;
                }
            }
        }
        return perfiles;
    }

    public void actualizarEstadoPerfil(int idPerfil, boolean activo) throws SQLException {
        try {
            perfilDAO.actualizarEstadoPerfil(idPerfil, activo);
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el perfil: " + e.getMessage(), e);
        }
    }

    public void actualizarPerfil(Perfil perfil) throws SQLException {
        try {
            perfilDAO.actualizarPerfil(perfil);
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el perfil: " + e.getMessage(), e);
        }
    }

}
