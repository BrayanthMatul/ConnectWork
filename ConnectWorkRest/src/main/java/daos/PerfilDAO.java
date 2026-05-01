package daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
