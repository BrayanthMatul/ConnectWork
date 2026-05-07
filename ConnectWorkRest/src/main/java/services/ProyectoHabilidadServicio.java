package services;

import java.sql.SQLException;
import java.util.List;

import daos.ProyectoHabilidadDAO;
import models.ProyectoHabilidad;

public class ProyectoHabilidadServicio {

    private final ProyectoHabilidadDAO proyectoHabilidadDAO;

    public ProyectoHabilidadServicio() {
        this.proyectoHabilidadDAO = new ProyectoHabilidadDAO();
    }

    public void guardarProyectoHabilidades(List<ProyectoHabilidad> habilidades) throws SQLException {
        try {
            for (ProyectoHabilidad habilidad : habilidades) {
                proyectoHabilidadDAO.insertar(habilidad);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al guardar las habilidades del proyecto: " + e.getMessage(), e);
        }
    }

    public List<ProyectoHabilidad> obtenerTodo() throws SQLException {
        try {
            return proyectoHabilidadDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las habilidades de los proyectos: " + e.getMessage(), e);
        }
    }

}
