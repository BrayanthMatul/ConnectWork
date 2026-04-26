package models;

import java.math.BigDecimal;
import java.sql.Date;

import enums.EstadoContrato;

public class Contrato { 
    
    private int id;
    private int idPropuesta;
    private Date fechaInicio;
    private BigDecimal monto;
    private EstadoContrato estado;
    private Double calificacion;

    public Contrato(int id, int idPropuesta, Date fechaInicio, BigDecimal monto, EstadoContrato estado, Double calificacion) {
        this.id = id;
        this.idPropuesta = idPropuesta;
        this.fechaInicio = fechaInicio;
        this.monto = monto;
        this.estado = estado;
        this.calificacion = calificacion;
    }

    public int getId() {
        return id;
    }

    public int getIdPropuesta() {
        return idPropuesta;
    }

    public Date getFechaInicio() {
        return fechaInicio;
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

    public void setIdPropuesta(int idPropuesta) {
        this.idPropuesta = idPropuesta;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
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
