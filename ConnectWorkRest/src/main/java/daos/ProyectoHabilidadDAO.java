package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConexionDB;
import models.ProyectoHabilidad;

public class ProyectoHabilidadDAO {

    public void insertar(ProyectoHabilidad proyectoHabilidad) throws SQLException {
        String query = "INSERT INTO proyecto_habilidades (id_proyecto, id_habilidad) VALUES (?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, proyectoHabilidad.getIdProyecto());
            ps.setInt(2, proyectoHabilidad.getIdHabilidad());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al registrar la nueva habilidad del proyecto: " + e.getMessage(), e);
        }
    }

    public List<ProyectoHabilidad> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM proyecto_habilidades";
        List<ProyectoHabilidad> proyectoHabilidades = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int idProyecto = rs.getInt("id_proyecto");
                int idHabilidad = rs.getInt("id_habilidad");
                proyectoHabilidades.add(new ProyectoHabilidad(idProyecto, idHabilidad));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las habilidades de los proyectos: " + e.getMessage(), e);
        }
        return proyectoHabilidades;
    }

}
