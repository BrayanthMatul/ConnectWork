package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.ConexionDB;
import enums.EstadoProyecto;
import models.Proyecto;

public class ProyectoDAO {

    public int insertar(Proyecto proyecto) throws SQLException {
        String query = "INSERT INTO proyectos (id_cliente, id_categoria, titulo, descripcion,  presupuesto_maximo, fecha_limite, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, proyecto.getIdCliente());
            ps.setInt(2, proyecto.getIdCategoria());
            ps.setString(3, proyecto.getTitulo());
            ps.setString(4, proyecto.getDescripcion());
            ps.setBigDecimal(5, proyecto.getPresupuestoMaximo());
            ps.setDate(6, proyecto.getFechaLimite());
            ps.setString(7, proyecto.getEstado().name());
            ps.executeUpdate();

            // Obtener el ID generado del nuevo proyecto
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new SQLException("Error al obtener el ID del nuevo proyecto: ");
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo proyecto: " + e.getMessage(), e);
        }
    }

    public Proyecto obtenerPorID(int id) throws SQLException {
        String query = "SELECT * FROM proyectos WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idCliente = rs.getInt("id_cliente");
                    int idCategoria = rs.getInt("id_categoria");
                    String titulo = rs.getString("titulo");
                    String descripcion = rs.getString("descripcion");
                    java.math.BigDecimal presupuestoMaximo = rs.getBigDecimal("presupuesto_maximo");
                    java.sql.Date fechaLimite = rs.getDate("fecha_limite");
                    String estado = rs.getString("estado");
                    EstadoProyecto estadoProyecto = EstadoProyecto.valueOf(estado);
                    return new Proyecto(id, idCliente, idCategoria, titulo, descripcion, presupuestoMaximo, fechaLimite,
                            estadoProyecto, null);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el proyecto: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Proyecto> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM proyectos";
        List<Proyecto> proyectos = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idCliente = rs.getInt("id_cliente");
                int idCategoria = rs.getInt("id_categoria");
                String titulo = rs.getString("titulo");
                String descripcion = rs.getString("descripcion");
                java.math.BigDecimal presupuestoMaximo = rs.getBigDecimal("presupuesto_maximo");
                java.sql.Date fechaLimite = rs.getDate("fecha_limite");
                String estado = rs.getString("estado");
                EstadoProyecto estadoProyecto = EstadoProyecto.valueOf(estado);
                proyectos.add(new Proyecto(id, idCliente, idCategoria, titulo, descripcion, presupuestoMaximo,
                        fechaLimite, estadoProyecto, null));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los proyectos: " + e.getMessage(), e);
        }
        return proyectos;
    }

    public boolean actualizarEstado(int id, EstadoProyecto nuevoEstado) throws SQLException {
        String query = "UPDATE proyectos SET estado = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado del proyecto: " + e.getMessage(), e);
        }
    }

    public List<Proyecto> obtenerTodosLosProyectos(int idCliente) throws SQLException {
        String query = "SELECT * FROM proyectos WHERE id_cliente = ?";
        List<Proyecto> proyectos = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idCategoria = rs.getInt("id_categoria");
                    String titulo = rs.getString("titulo");
                    String descripcion = rs.getString("descripcion");
                    java.math.BigDecimal presupuestoMaximo = rs.getBigDecimal("presupuesto_maximo");
                    java.sql.Date fechaLimite = rs.getDate("fecha_limite");
                    String estado = rs.getString("estado");
                    EstadoProyecto estadoProyecto = EstadoProyecto.valueOf(estado);
                    proyectos.add(new Proyecto(id, idCliente, idCategoria, titulo, descripcion, presupuestoMaximo,
                            fechaLimite, estadoProyecto, null));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los proyectos del cliente: " + e.getMessage(), e);
        }
        return proyectos;
    }

    public List<Proyecto> obtenerProyectosEnCursoConNombre(int idCliente, String titulo) throws SQLException {
        String query = "SELECT * FROM proyectos WHERE id_cliente = ? AND titulo = ? AND estado IN ('ABIERTO', 'EN_REVISION', 'EN_PROGRESO', 'ENTREGA_PENDIENTE')";
        List<Proyecto> proyectos = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idCliente);
            ps.setString(2, titulo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idCategoria = rs.getInt("id_categoria");
                    String descripcion = rs.getString("descripcion");
                    java.math.BigDecimal presupuestoMaximo = rs.getBigDecimal("presupuesto_maximo");
                    java.sql.Date fechaLimite = rs.getDate("fecha_limite");
                    String estado = rs.getString("estado");
                    EstadoProyecto estadoProyecto = EstadoProyecto.valueOf(estado);
                    proyectos.add(new Proyecto(id, idCliente, idCategoria, titulo, descripcion, presupuestoMaximo,
                            fechaLimite, estadoProyecto, null));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los proyectos en curso: " + e.getMessage(), e);
        }
        return proyectos;
    }

    public boolean actualizarEstadoProyecto(int id, EstadoProyecto nuevoEstado) throws SQLException {
        return actualizarEstado(id, nuevoEstado);
    }

}
