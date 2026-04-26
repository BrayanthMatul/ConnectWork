package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Recarga {
    
    private int id;
    private int idCliente;
    private BigDecimal monto;
    private LocalDateTime fechaHora;

    public Recarga(int id, int idCliente, BigDecimal monto, LocalDateTime fechaHora) {
        this.id = id;
        this.idCliente = idCliente;
        this.monto = monto;
        this.fechaHora = fechaHora;
    }

    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
}
