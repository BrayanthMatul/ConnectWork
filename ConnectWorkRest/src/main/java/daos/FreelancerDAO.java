package daos;

import database.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.Freelancer;

public class FreelancerDAO {

    public void registrarNuevoFreelancer(Freelancer freelancerNuevo) throws SQLException {
        String query = "INSERT INTO freelancers (id_freelancer, biografia, nivel_experiencia, calificacion, tarifa_hora) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, freelancerNuevo.getIdFreelancer());
            ps.setString(2, freelancerNuevo.getBiografia());
            ps.setString(3, freelancerNuevo.getNivelExperiencia());
            ps.setFloat(4, freelancerNuevo.getCalificacion());
            ps.setBigDecimal(5, freelancerNuevo.getTarifaHora());
            ps.executeUpdate();

            PerfilDAO perfilDAO = new PerfilDAO();
            perfilDAO.marcarPerfilComoCompleto(freelancerNuevo.getIdFreelancer());
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo freelancer: " + e.getMessage(), e);
        }
    }
}
