package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaldoSistema {

    private int id;
    private int idContrato;
    private LocalDateTime fechaHora;
    private BigDecimal porcentajeComision;
    private BigDecimal montoBruto;
    private BigDecimal montoComision;

    public SaldoSistema(int id, int idContrato, LocalDateTime fechaHora, BigDecimal porcentajeComision, BigDecimal montoBruto, BigDecimal montoComision) {
        this.id = id;
        this.idContrato = idContrato;
        this.fechaHora = fechaHora;
        this.porcentajeComision = porcentajeComision;
        this.montoBruto = montoBruto;
        this.montoComision = montoComision;
    }

    public int getId() {
        return id;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public BigDecimal getPorcentajeComision() {
        return porcentajeComision;
    }

    public BigDecimal getMontoBruto() {
        return montoBruto;
    }

    public BigDecimal getMontoComision() {
        return montoComision;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setPorcentajeComision(BigDecimal porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public void setMontoBruto(BigDecimal montoBruto) {
        this.montoBruto = montoBruto;
    }

    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
    }
}
