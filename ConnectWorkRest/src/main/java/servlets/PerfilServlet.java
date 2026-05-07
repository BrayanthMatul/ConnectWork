package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exceptions.CorreoUsuarioException;
import exceptions.CuiUsuarioException;
import exceptions.NombreUsuarioException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.EstadoRequest;
import models.Perfil;
import services.PerfilServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/perfil" })
public class PerfilServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private final PerfilServicio perfilServicio = new PerfilServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Perfil perfil = gson.fromJson(req.getReader(), Perfil.class);
            perfilServicio.crearPerfil(perfil);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Perfil creado exitosamente");
        } catch (NombreUsuarioException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            JsonUtil.escribirJson(res, "error", "El nombre de usuario ya existe.");
        } catch (CorreoUsuarioException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            JsonUtil.escribirJson(res, "error", "El correo electrónico ya está registrado.");
        } catch (CuiUsuarioException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            JsonUtil.escribirJson(res, "error", "El perfil con el CUI proporcionado ya existe.");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al crear el perfil en la base de datos: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAutenticacion(req, res)) {
                return;
            }

            List<Perfil> perfiles = perfilServicio.obtenerTodosLosPerfiles();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(gson.toJson(perfiles));
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al obtener perfiles: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {

        // solo lo prodra actualizar un freelancer o cliente
        try {

            Perfil perfil = gson.fromJson(req.getReader(), Perfil.class);
            perfilServicio.actualizarPerfil(perfil);
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "mensaje", "Perfil actualizado exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar el perfil: " + e.getMessage());
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Verificar que sea administrador
            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }

            // Obtener el estado del body
            EstadoRequest estadoData = gson.fromJson(req.getReader(), EstadoRequest.class);
            perfilServicio.actualizarEstadoPerfil(estadoData.getId(), estadoData.isActivo());
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "mensaje", "Estado del perfil actualizado exitosamente");
        } catch (NumberFormatException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.escribirJson(res, "error", "ID de perfil inválido");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar el estado: " + e.getMessage());
        }
    }

}
