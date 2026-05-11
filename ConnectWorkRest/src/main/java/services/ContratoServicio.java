package services;

import java.sql.SQLException;
import java.util.List;

import daos.ContratoDAO;
import models.Contrato;
import models.EstadoContratoRequest;

public class ContratoServicio {

    private final ContratoDAO contratoDAO;

    public ContratoServicio() {
        this.contratoDAO = new ContratoDAO();
    }

    public void registrarNuevoContrato(Contrato contrato) throws SQLException {
        try {
            contratoDAO.insertar(contrato);
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo contrato: " + e.getMessage(), e);
        }
    }

    public List<Contrato> obtenerTodosLosContratos() throws SQLException {
        try {
            return contratoDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener todos los contratos: " + e.getMessage(), e);
        }
    }

    public void actualizarEstado(EstadoContratoRequest request) throws SQLException {
        try {
            contratoDAO.actualizarEstado(request.getIdContrato(), request.getNuevoEstado());
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado del contrato: " + e.getMessage(), e);
        }
    }

}
