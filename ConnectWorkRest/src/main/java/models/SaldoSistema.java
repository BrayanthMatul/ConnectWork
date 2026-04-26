package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaldoSistema {

    private int id;
    private int id_contrato;
    private LocalDateTime fecha_hora;
    private BigDecimal porcentaje_comision;
    private BigDecimal monto_bruto;
    private BigDecimal monto_comision;

    public SaldoSistema(int id, int id_contrato, LocalDateTime fecha_hora, BigDecimal porcentaje_comision, BigDecimal monto_bruto, BigDecimal monto_comision) {
        this.id = id;
        this.id_contrato = id_contrato;
        this.fecha_hora = fecha_hora;
        this.porcentaje_comision = porcentaje_comision;
        this.monto_bruto = monto_bruto;
        this.monto_comision = monto_comision;
    }

    public int getId() {
        return id;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public BigDecimal getPorcentaje_comision() {
        return porcentaje_comision;
    }

    public BigDecimal getMonto_bruto() {
        return monto_bruto;
    }

    public BigDecimal getMonto_comision() {
        return monto_comision;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public void setPorcentaje_comision(BigDecimal porcentaje_comision) {
        this.porcentaje_comision = porcentaje_comision;
    }

    public void setMonto_bruto(BigDecimal monto_bruto) {
        this.monto_bruto = monto_bruto;
    }

    public void setMonto_comision(BigDecimal monto_comision) {
        this.monto_comision = monto_comision;
    }

    
    
}

// id INT AUTO_INCREMENT PRIMARY KEY,
//     id_contrato INT NOT NULL UNIQUE,
//     fecha_hora DATETIME NOT NULL,
//     porcentaje_comision DECIMAL(5, 2) NOT NULL,
//     monto_bruto DECIMAL(10, 2) NOT NULL,
//     monto_comision DECIMAL(10, 2) NOT NULL,
