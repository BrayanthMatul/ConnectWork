package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConexionDB;
import models.FreelancerHabilidad;

public class FreelancerHabilidadDAO {

    public void insertar(FreelancerHabilidad freelancerHabilidad) throws SQLException {
        String query = "INSERT INTO freelancer_habilidades (id_freelancer, id_habilidad) VALUES (?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, freelancerHabilidad.getIdFreelancer());
            ps.setInt(2, freelancerHabilidad.getIdHabilidad());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al registrar la habilidad del freelancer: " + e.getMessage(), e);
        }
    }

    public List<FreelancerHabilidad> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM freelancer_habilidades";
        List<FreelancerHabilidad> freelancerHabilidades = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int idFreelancer = rs.getInt("id_freelancer");
                int idHabilidad = rs.getInt("id_habilidad");
                freelancerHabilidades.add(new FreelancerHabilidad(idFreelancer, idHabilidad));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las habilidades de los freelancers: " + e.getMessage(), e);
        }
        return freelancerHabilidades;
    }

}
