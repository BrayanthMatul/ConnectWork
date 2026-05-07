package services;

import java.sql.SQLException;
import java.util.List;

import daos.CategoriaDAO;
import daos.CategoriaSolicitudDAO;
import exceptions.NombreRepetidoException;
import models.CategoriaSolicitud;
import models.EstadoRequest;

public class CategoriaSolicitudServicio {

    private final CategoriaSolicitudDAO categoriaSolicitudDAO;

    public CategoriaSolicitudServicio() {
        this.categoriaSolicitudDAO = new CategoriaSolicitudDAO();
    }

    public void registrarSolicitud(CategoriaSolicitud solicitud) throws SQLException, NombreRepetidoException {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        if (categoriaDAO.obtenerPorNombre(solicitud.getNombre()) == null) {
            categoriaSolicitudDAO.insertarCategoriaSolicitud(solicitud);
        } else {
            throw new NombreRepetidoException(" El nombre de la categoría ya existe. Por favor, elige otro nombre.");
        }

    }

    public List<CategoriaSolicitud> obtenerTodasLasSolicitudes() throws SQLException {
        return categoriaSolicitudDAO.obtenerTodas();
    }

    public void actualizarEstadoSolicitud(EstadoRequest estado) throws SQLException {
        categoriaSolicitudDAO.actualizarEstadoSolicitud(estado.getId(), estado.isActivo());
    }

}
