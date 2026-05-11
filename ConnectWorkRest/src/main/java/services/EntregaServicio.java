package services;

import java.sql.SQLException;
import java.util.List;

import daos.EntregaDAO;
import models.Entrega;

public class EntregaServicio {

    private final EntregaDAO entregaDAO;

    public EntregaServicio() {
        this.entregaDAO = new EntregaDAO();
    }

    public void registrarNuevaEntrega(Entrega entrega) throws SQLException {
        try {
            entregaDAO.insertar(entrega);
        } catch (SQLException e) {
            throw new SQLException("Error al registrar la entrega: " + e.getMessage(), e);
        }
    }

    public List<Entrega> obtenerTodasLasEntregas() throws SQLException {
        try {
            return entregaDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las entregas: " + e.getMessage(), e);
        }
    }

    public void actualizarEstadoEntrega(int idEntrega, boolean aceptado, String motivoRechazo) throws SQLException {
        try {
            entregaDAO.actualizarEstado(idEntrega, aceptado, motivoRechazo);
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado de la entrega: " + e.getMessage(), e);
        }
    }

}
