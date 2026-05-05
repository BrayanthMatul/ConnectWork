package services;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import daos.SaldoSistemaDAO;
import models.SaldoSistema;

public class SaldoSistemaServicio {

    private final SaldoSistemaDAO saldoSistemaDAO;

    public SaldoSistemaServicio() {
        this.saldoSistemaDAO = new SaldoSistemaDAO();
    }

    public void registrarNuevoSaldoSistema(SaldoSistema saldoSistema) throws SQLException {
        try {
            saldoSistemaDAO.insertar(saldoSistema);
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el nuevo saldo del sistema: " + e.getMessage(), e);
        }
    }

    public List<SaldoSistema> obtenerTodosLosRegistros() throws SQLException {
        try {
            return saldoSistemaDAO.obtenerTodos();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los registros de saldo: " + e.getMessage(), e);
        }
    }

    public BigDecimal obtenerTotalSaldo() throws SQLException {
        try {
            return saldoSistemaDAO.totalSaldo();
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el total del saldo: " + e.getMessage(), e);
        }
    }

}
