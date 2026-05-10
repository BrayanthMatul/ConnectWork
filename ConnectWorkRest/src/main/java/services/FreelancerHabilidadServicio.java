package services;

import java.sql.SQLException;
import java.util.List;

import daos.FreelancerHabilidadDAO;
import models.FreelancerHabilidad;

public class FreelancerHabilidadServicio {

    private final FreelancerHabilidadDAO freelancerHabilidadDAO;

    public FreelancerHabilidadServicio() {
        this.freelancerHabilidadDAO = new FreelancerHabilidadDAO();
    }

    public void guardarFreelancerHabilidades(List<FreelancerHabilidad> habilidades) throws SQLException {
        try {
            for (FreelancerHabilidad habilidad : habilidades) {
                freelancerHabilidadDAO.insertar(habilidad);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al guardar las habilidades del freelancer: " + e.getMessage(), e);
        }
    }

    public List<FreelancerHabilidad> obtenerTodo() throws SQLException {
        try {
            return freelancerHabilidadDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las habilidades de los freelancers: " + e.getMessage(), e);
        }
    }

}
