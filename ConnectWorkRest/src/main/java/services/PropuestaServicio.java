package services;

import java.sql.SQLException;
import java.util.List;

import daos.PropuestaDAO;
import models.EstadoPropuestaRequest;
import models.Propuesta;

public class PropuestaServicio {

    private final PropuestaDAO propuestaDAO;

    public PropuestaServicio() {
        this.propuestaDAO = new PropuestaDAO();
    }

    public void registrarNuevaPropuesta(Propuesta propuesta) throws SQLException {
        try {
            propuestaDAO.insertar(propuesta);
        } catch (SQLException e) {
            throw new SQLException("Error al registrar la nueva propuesta: " + e.getMessage(), e);
        }
    }

    public List<Propuesta> obtenerTodos() throws SQLException {
        try {
            // imprimir el id de cada propuesta obtenida del DAO
            for (Propuesta propuesta : propuestaDAO.obtenerTodos()) {
                System.out.println("ID de la propuesta: " + propuesta.getId());
            }
            return propuestaDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener todas las propuestas: " + e.getMessage(), e);
        }
    }

    public boolean actualizarEstado(EstadoPropuestaRequest request) throws SQLException {
        try {
            return propuestaDAO.actualizarEstado(request.getIdPropuesta(), request.getNuevoEstado());
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado de la propuesta: " + e.getMessage(), e);
        }
    }

}
