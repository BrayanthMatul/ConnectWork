package utils;

import java.io.IOException;
import java.sql.SQLException;

import enums.TipoUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.UsuarioServicio;

/**
 * Utilidad para validar permisos y autenticación en servlets
 */
public class PermissionUtil {

    private static final UsuarioServicio usuarioServicio = new UsuarioServicio();

    /**
     * Verifica que el usuario esté autenticado
     * 
     * @return true si está autenticado, false en caso contrario
     */
    public static boolean verificarAutenticacion(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String usernameAutenticado = (String) req.getAttribute("username");

        if (usernameAutenticado == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.escribirJson(res, "error", "Usuario no autenticado");
            return false;
        }
        return true;
    }

    /**
     * Verifica que el usuario sea administrador
     * 
     * @return true si es admin, false en caso contrario
     */
    public static boolean verificarAdministrador(HttpServletRequest req, HttpServletResponse res)
            throws IOException, SQLException {
        String usernameAutenticado = (String) req.getAttribute("username");

        if (usernameAutenticado == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.escribirJson(res, "error", "Usuario no autenticado");
            return false;
        }

        if (!usuarioServicio.esAdministrador(usernameAutenticado)) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            JsonUtil.escribirJson(res, "error", "Solo administradores pueden realizar esta acción");
            return false;
        }

        return true;
    }

    /**
     * Verifica que el usuario sea cliente
     * 
     * @return true si es cliente, false en caso contrario
     */
    public static boolean verificarCliente(HttpServletRequest req, HttpServletResponse res)
            throws IOException, SQLException {
        String usernameAutenticado = (String) req.getAttribute("username");

        if (usernameAutenticado == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.escribirJson(res, "error", "Usuario no autenticado");
            return false;
        }

        if (!usuarioServicio.esCliente(usernameAutenticado)) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            JsonUtil.escribirJson(res, "error", "Solo clientes pueden realizar esta acción");
            return false;
        }

        return true;
    }

    /**
     * Verifica que el usuario sea freelancer
     * 
     * @return true si es freelancer, false en caso contrario
     */
    public static boolean verificarFreelancer(HttpServletRequest req, HttpServletResponse res)
            throws IOException, SQLException {
        String usernameAutenticado = (String) req.getAttribute("username");

        if (usernameAutenticado == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.escribirJson(res, "error", "Usuario no autenticado");
            return false;
        }

        if (!usuarioServicio.esFreelancer(usernameAutenticado)) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            JsonUtil.escribirJson(res, "error", "Solo freelancers pueden realizar esta acción");
            return false;
        }

        return true;
    }

    /**
     * Verifica que el usuario tenga uno de los tipos especificados
     * 
     * @return true si cumple, false en caso contrario
     */
    public static boolean verificarTipos(HttpServletRequest req, HttpServletResponse res,
            TipoUsuario... tipos) throws IOException, SQLException {
        String usernameAutenticado = (String) req.getAttribute("username");

        if (usernameAutenticado == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.escribirJson(res, "error", "Usuario no autenticado");
            return false;
        }

        TipoUsuario tipoUsuario = usuarioServicio.obtenerUsuario(usernameAutenticado).getTipoUsuario();

        for (TipoUsuario tipo : tipos) {
            if (tipoUsuario == tipo) {
                return true;
            }
        }

        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        JsonUtil.escribirJson(res, "error", "No tienes permisos para realizar esta acción");
        return false;
    }

    /**
     * Obtiene el username del usuario autenticado
     */
    public static String obtenerUsernameAutenticado(HttpServletRequest req) {
        return (String) req.getAttribute("username");
    }
}
