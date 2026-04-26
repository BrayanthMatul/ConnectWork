package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Recarga {
    
    private int id;
    private int id_cliente;
    private BigDecimal monto;
    private LocalDateTime fecha_hora;

    public Recarga(int id, int id_cliente, BigDecimal monto, LocalDateTime fecha_hora) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.monto = monto;
        this.fecha_hora = fecha_hora;
    }

    public int getId() {
        return id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
    
}
