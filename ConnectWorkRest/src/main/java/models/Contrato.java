package models;

import java.math.BigDecimal;
import java.sql.Date;

import enums.EstadoContrato;

public class Contrato { 
    
    private int id;
    private int id_propuesta;
    private Date fecha_inicio;
    private BigDecimal monto;
    private EstadoContrato estado;
    private Double calificacion;

    public Contrato(int id, int id_propuesta, Date fecha_inicio, BigDecimal monto, EstadoContrato estado, Double calificacion) {
        this.id = id;
        this.id_propuesta = id_propuesta;
        this.fecha_inicio = fecha_inicio;
        this.monto = monto;
        this.estado = estado;
        this.calificacion = calificacion;
    }

    public int getId() {
        return id;
    }

    public int getId_propuesta() {
        return id_propuesta;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_propuesta(int id_propuesta) {
        this.id_propuesta = id_propuesta;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
   
}
