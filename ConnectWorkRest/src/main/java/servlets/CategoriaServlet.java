package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.NombreRepetidoException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Categoria;
import models.EstadoRequest;
import services.CategoriaServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = { "/api/categoria" })
public class CategoriaServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private final CategoriaServicio categoriaServicio = new CategoriaServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }
            Categoria categoria = gson.fromJson(req.getReader(), Categoria.class);
            categoriaServicio.crearNuevaCategoria(categoria);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "mensaje", "Categoría creada exitosamente");
        } catch (NombreRepetidoException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            JsonUtil.escribirJson(res, "error",
                    "No se pudo registrar la categoría. Ya existe una categoría con ese nombre.");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al crear la categoría: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            List<Categoria> categorias = categoriaServicio.obtenerTodasLasCategorias();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(gson.toJson(categorias));
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al obtener categorías: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }
            Categoria categoria = gson.fromJson(req.getReader(), Categoria.class);
            categoriaServicio.actualizarCategoria(categoria);
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "mensaje", "Categoría actualizada exitosamente");
        } catch (NombreRepetidoException e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            JsonUtil.escribirJson(res, "error",
                    "No se pudo actualizar la categoría. Ya existe una categoría con ese nombre.");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar la categoría: " + e.getMessage());
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAdministrador(req, res)) {
                return;
            }
            EstadoRequest estadoData = gson.fromJson(req.getReader(), EstadoRequest.class);
            categoriaServicio.actualizarEstadoCategoria(estadoData.getId(), estadoData.isActivo());
            res.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.escribirJson(res, "mensaje", "Estado de la categoría actualizado exitosamente");
        } catch (NumberFormatException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.escribirJson(res, "error", "ID de categoría inválido");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al actualizar el estado: " + e.getMessage());
        }
    }
}
