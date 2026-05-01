package servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exceptions.CorreoUsuarioException;
import exceptions.CuiUsuarioException;
import exceptions.NombreUsuarioException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Perfil;
import services.PerfilServicio;
import utils.JsonUtil;

@WebServlet(urlPatterns = { "/api/perfil" })
public class PerfilServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Perfil perfil = gson.fromJson(req.getReader(), Perfil.class);
            PerfilServicio servicioPerfil = new PerfilServicio();
            servicioPerfil.crearPerfil(perfil);
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

}
