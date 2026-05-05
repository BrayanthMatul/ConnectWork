package services;

import daos.HabilidadDAO;
import exceptions.NombreRepetidoException;
import models.Habilidad;
import java.sql.SQLException;
import java.util.List;

public class HabilidadServicio {
    private final HabilidadDAO habilidadDAO;

    public HabilidadServicio() {
        this.habilidadDAO = new HabilidadDAO();
    }

    public void registrarNuevaHabilidad(Habilidad habilidad) throws SQLException, NombreRepetidoException {
        Habilidad existente = habilidadDAO.obtenerPorNombre(habilidad.getNombre());
        if (existente != null) {
            throw new NombreRepetidoException("Ya existe una habilidad con ese nombre.");
        }
        habilidadDAO.insertar(habilidad);
    }

    public List<Habilidad> obtenerTodasLasHabilidades() throws SQLException {
        return habilidadDAO.obtenerTodos();
    }

    public void actualizarEstadoHabilidad(int id, boolean activo) throws SQLException {
        habilidadDAO.actualizarEstado(id, activo);
    }

    public void actualizarHabilidad(Habilidad habilidad) throws SQLException, NombreRepetidoException {
        Habilidad existente = habilidadDAO.obtenerPorNombre(habilidad.getNombre());
        if (existente != null && existente.getId() != habilidad.getId()) {
            throw new NombreRepetidoException("Ya existe una habilidad con ese nombre.");
        }
        habilidadDAO.actualizar(habilidad);
    }
}
