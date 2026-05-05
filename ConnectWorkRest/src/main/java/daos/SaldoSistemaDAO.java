package daos;

import database.ConexionDB;
import models.SaldoSistema;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaldoSistemaDAO {

    public void insertar(SaldoSistema saldoSistema) throws SQLException {
        String query = "INSERT INTO saldos_sistema (id_contrato, fecha_hora, porcentaje_comision, monto_bruto, monto_comision) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, saldoSistema.getIdContrato());
            ps.setTimestamp(2, Timestamp.valueOf(saldoSistema.getFechaHora()));
            ps.setBigDecimal(3, saldoSistema.getPorcentajeComision());
            ps.setBigDecimal(4, saldoSistema.getMontoBruto());
            ps.setBigDecimal(5, saldoSistema.getMontoComision());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar el saldo del sistema: " + e.getMessage(), e);
        }
    }

    public List<SaldoSistema> obtenerTodos() throws SQLException {
        String query = "SELECT * FROM saldos_sistema";
        List<SaldoSistema> saldos = new ArrayList<>();
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idContrato = rs.getInt("id_contrato");
                LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();
                BigDecimal porcentajeComision = rs.getBigDecimal("porcentaje_comision");
                BigDecimal montoBruto = rs.getBigDecimal("monto_bruto");
                BigDecimal montoComision = rs.getBigDecimal("monto_comision");
                saldos.add(new SaldoSistema(id, idContrato, fechaHora, porcentajeComision, montoBruto, montoComision));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los saldos del sistema: " + e.getMessage(), e);
        }
        return saldos;
    }

    public BigDecimal totalSaldo() throws SQLException {
        String query = "SELECT SUM(monto_comision) as total FROM saldos_sistema";
        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total");
                return total != null ? total : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al calcular el total de saldo: " + e.getMessage(), e);
        }
        return BigDecimal.ZERO;
    }

}
