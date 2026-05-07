package daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

import database.ConexionDB;
import models.Perfil;

public class PerfilDAO {

    public void registrarNuevoPerfil(Perfil perfil) throws SQLException {
        String query = "INSERT INTO perfiles (id_perfil, cui, telefono, fecha_nacimiento, direccion, saldo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, perfil.getIdPerfil());
            ps.setString(2, perfil.getCui());
            ps.setString(3, perfil.getTelefono());
            ps.setDate(4, perfil.getFechaNacimiento());
            ps.setString(5, perfil.getDireccion());
            ps.setBigDecimal(6, perfil.getSaldo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el perfil: " + e.getMessage(), e);
        }
    }

    public Perfil obtenerPerfilPorCui(String cuiIngresado) throws SQLException {
        String query = "SELECT * FROM perfiles WHERE cui = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, cuiIngresado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idPerfil = rs.getInt("id_perfil");
                    String cui = rs.getString("cui");
                    String telefono = rs.getString("telefono");
                    Date fechaNacimiento = rs.getDate("fecha_nacimiento");
                    String direccion = rs.getString("direccion");
                    BigDecimal saldo = rs.getBigDecimal("saldo");
                    boolean activo = rs.getBoolean("activo");
                    boolean perfilCompleto = rs.getBoolean("perfil_completado");
                    return new Perfil(idPerfil, cui, telefono, fechaNacimiento, direccion, saldo, activo,
                            perfilCompleto, null);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el perfil por CUI: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Perfil> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM perfiles";
        List<Perfil> perfiles = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int idPerfil = rs.getInt("id_perfil");
                String cui = rs.getString("cui");
                String telefono = rs.getString("telefono");
                Date fechaNacimiento = rs.getDate("fecha_nacimiento");
                String direccion = rs.getString("direccion");
                BigDecimal saldo = rs.getBigDecimal("saldo");
                boolean activo = rs.getBoolean("activo");
                boolean perfilCompleto = rs.getBoolean("perfil_completado");
                perfiles.add(new Perfil(idPerfil, cui, telefono, fechaNacimiento, direccion, saldo,
                        activo, perfilCompleto, null));

            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los perfiles: " + e.getMessage(), e);
        }
        return perfiles;
    }

    public void actualizarEstadoPerfil(int idPerfil, boolean activo) throws SQLException {
        String query = "UPDATE perfiles SET activo = ? WHERE id_perfil = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setBoolean(1, activo);
            ps.setInt(2, idPerfil);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado del perfil: " + e.getMessage(), e);
        }
    }

    public void actualizarPerfil(Perfil perfil) throws SQLException {
        String query = "UPDATE perfiles SET cui = ?, telefono = ?, fecha_nacimiento = ?, direccion = ? WHERE id_perfil = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, perfil.getCui());
            ps.setString(2, perfil.getTelefono());
            ps.setDate(3, perfil.getFechaNacimiento());
            ps.setString(4, perfil.getDireccion());
            ps.setInt(5, perfil.getIdPerfil());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el perfil: " + e.getMessage(), e);
        }
    }

    public void marcarPerfilComoCompleto(int idPerfil) throws SQLException {
        String query = "UPDATE perfiles SET perfil_completado = TRUE WHERE id_perfil = ?";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPerfil);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al marcar el perfil como completo: " + e.getMessage(), e);
        }
    }

}
