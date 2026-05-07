package services;

import java.sql.SQLException;
import java.util.List;

import daos.ProyectoDAO;
import exceptions.NombreRepetidoException;
import models.EstadoProyectoRequest;
import models.Proyecto;
import models.ProyectoHabilidad;

public class ProyectoServicio {

    private final ProyectoDAO proyectoDAO;
    private final ProyectoHabilidadServicio proyectoHabilidadServicio;

    public ProyectoServicio() {
        this.proyectoDAO = new ProyectoDAO();
        this.proyectoHabilidadServicio = new ProyectoHabilidadServicio();
    }

    public void registrarNuevoProyecto(Proyecto proyecto) throws SQLException, NombreRepetidoException {
        try {
            validarProyectoDuplicado(proyecto);
            int id = proyectoDAO.insertar(proyecto);
            this.guardarHabilidadesProyecto(id, proyecto.getHabilidades());
        } catch (NombreRepetidoException e) {
            throw new NombreRepetidoException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo proyecto: " + e.getMessage(), e);
        }
    }

    private void validarProyectoDuplicado(Proyecto proyecto) throws SQLException, NombreRepetidoException {
        List<Proyecto> proyectosEnCurso = proyectoDAO.obtenerProyectosEnCursoConNombre(proyecto.getIdCliente(),
                proyecto.getTitulo());
        if (!proyectosEnCurso.isEmpty()) {
            throw new NombreRepetidoException("Ya existe un proyecto en curso del mismo usuario con el mismo nombre.");
        }
    }

    public List<Proyecto> obtenerTodosLosProyectos() throws SQLException {
        try {
            return proyectoDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener todos los proyectos: " + e.getMessage(), e);
        }
    }

    public boolean actualizarEstado(EstadoProyectoRequest request) throws SQLException {
        try {
            return proyectoDAO.actualizarEstado(request.getIdProyecto(), request.getNuevoEstado());
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado del proyecto: " + e.getMessage(), e);
        }
    }

    private void guardarHabilidadesProyecto(int idProyecto, List<ProyectoHabilidad> habilidades) throws SQLException {
        try {
            for (ProyectoHabilidad habilidad : habilidades) {
                habilidad.setIdProyecto(idProyecto);
            }
            proyectoHabilidadServicio.guardarProyectoHabilidades(habilidades);
        } catch (SQLException e) {
            throw new SQLException("Error al guardar las habilidades del proyecto: " + e.getMessage(), e);
        }
    }

}
