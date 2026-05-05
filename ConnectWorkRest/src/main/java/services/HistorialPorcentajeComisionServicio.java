package services;

import java.sql.SQLException;
import java.util.List;

import daos.HistorialPorcentajeComisionDAO;
import models.HistorialPorcentajeComision;

public class HistorialPorcentajeComisionServicio {

    private final HistorialPorcentajeComisionDAO historialComisionDAO;

    public HistorialPorcentajeComisionServicio() {
        this.historialComisionDAO = new HistorialPorcentajeComisionDAO();
    }

    public void registrarNuevoHistorialComision(HistorialPorcentajeComision historialComision) throws SQLException {
        try {
            historialComisionDAO.insertar(historialComision);
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo historial de comisión: " + e.getMessage(), e);
        }
    }

    public List<HistorialPorcentajeComision> obtenerTodosLosHistoriales() throws SQLException {
        try {
            return historialComisionDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los historiales de comisión: " + e.getMessage(), e);
        }
    }

}
