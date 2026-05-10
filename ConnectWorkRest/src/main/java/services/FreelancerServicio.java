package services;

import java.sql.SQLException;
import java.util.List;

import daos.FreelancerDAO;
import models.Freelancer;
import models.FreelancerHabilidad;

public class FreelancerServicio {

    private final FreelancerDAO freelancerDAO;
    private final FreelancerHabilidadServicio freelancerHabilidadServicio;

    public FreelancerServicio() {
        this.freelancerDAO = new FreelancerDAO();
        this.freelancerHabilidadServicio = new FreelancerHabilidadServicio();
    }

    public void registrarNuevoFreelancer(Freelancer freelancer) throws SQLException {
        try {
            freelancerDAO.registrarNuevoFreelancer(freelancer);
            this.guardarHabilidadesFreelancer(freelancer.getIdFreelancer(), freelancer.getHabilidades());
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo freelancer: " + e.getMessage(), e);
        }
    }

    private void guardarHabilidadesFreelancer(int idFreelancer, List<FreelancerHabilidad> habilidades)
            throws SQLException {
        try {
            for (FreelancerHabilidad habilidad : habilidades) {
                habilidad.setIdFreelancer(idFreelancer);
            }
            freelancerHabilidadServicio.guardarFreelancerHabilidades(habilidades);
        } catch (SQLException e) {
            throw new SQLException("Error al guardar las habilidades del freelancer: " + e.getMessage(), e);
        }
    }

}
