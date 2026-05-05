
package services;

import daos.CategoriaDAO;
import exceptions.NombreRepetidoException;
import models.Categoria;
import java.sql.SQLException;
import java.util.List;

public class CategoriaServicio {
    private final CategoriaDAO categoriaDAO;

    public CategoriaServicio() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public void crearNuevaCategoria(Categoria categoria) throws SQLException, NombreRepetidoException {
        Categoria existente = categoriaDAO.obtenerPorNombre(categoria.getNombre());
        if (existente != null) {
            throw new NombreRepetidoException("Ya existe una categoría con ese nombre.");
        }

        categoriaDAO.insertarCategoria(categoria);
    }

    public List<Categoria> obtenerTodasLasCategorias() throws SQLException {
        return categoriaDAO.obtenerTodas();
    }

    public void actualizarEstadoCategoria(int id, boolean activo) throws SQLException {
        categoriaDAO.actualizarEstado(id, activo);
    }

    public void actualizarCategoria(Categoria categoria) throws SQLException, NombreRepetidoException {
        Categoria existente = categoriaDAO.obtenerPorNombre(categoria.getNombre());
        if (existente != null && existente.getId() != categoria.getId()) {
            throw new NombreRepetidoException("Ya existe una categoría con ese nombre.");
        }
        categoriaDAO.actualizarCategoria(categoria);
    }
}
