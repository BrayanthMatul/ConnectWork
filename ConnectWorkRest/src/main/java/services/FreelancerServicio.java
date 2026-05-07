package services;

import java.sql.SQLException;

import daos.FreelancerDAO;
import models.Freelancer;

public class FreelancerServicio {

    private final FreelancerDAO freelancerDAO;

    public FreelancerServicio() {
        this.freelancerDAO = new FreelancerDAO();
    }

    public void registrarNuevoFreelancer(Freelancer freelancer) throws SQLException {
        try {
            freelancerDAO.registrarNuevoFreelancer(freelancer);
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo freelancer: " + e.getMessage(), e);
        }
    }

}
