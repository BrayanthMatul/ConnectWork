package services;

import java.sql.SQLException;
import java.util.List;

import daos.HabilidadDAO;
import daos.HabilidadSolicitudDAO;
import exceptions.NombreRepetidoException;
import models.EstadoRequest;
import models.HabilidadSolicitud;

public class HabilidadSolicitudServicio {

    private final HabilidadSolicitudDAO habilidadSolicitudDAO;

    public HabilidadSolicitudServicio() {
        this.habilidadSolicitudDAO = new HabilidadSolicitudDAO();
    }

    public void registrarSolicitud(HabilidadSolicitud solicitud) throws SQLException, NombreRepetidoException {
        HabilidadDAO habilidadDAO = new HabilidadDAO();
        List<models.Habilidad> habilidades = habilidadDAO.obtenerTodos();

        for (models.Habilidad habilidad : habilidades) {
            if (habilidad.getNombre().equals(solicitud.getNombre()) &&
                    habilidad.getIdCategoria() == solicitud.getIdCategoria()) {
                throw new NombreRepetidoException(
                        "Ya existe una habilidad con este nombre en la categoría seleccionada. Por favor, elige otro nombre.");
            }
        }

        habilidadSolicitudDAO.insertar(solicitud);
    }

    public List<HabilidadSolicitud> obtenerTodasLasSolicitudes() throws SQLException {
        return habilidadSolicitudDAO.obtenerTodos();
    }

    public void actualizarEstadoSolicitud(EstadoRequest estado) throws SQLException {
        habilidadSolicitudDAO.actualizarEstadoSolicitud(estado.getId(), estado.isActivo());
    }

}
