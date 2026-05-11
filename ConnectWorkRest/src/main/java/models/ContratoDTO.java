package models;

import java.math.BigDecimal;
import enums.EstadoContrato;

public class ContratoDTO {
    private int idPropuesta;
    private String fechaInicio;
    private BigDecimal monto;
    private EstadoContrato estado;
    private Double calificacion;

    public ContratoDTO(int idPropuesta, String fechaInicio, BigDecimal monto, EstadoContrato estado,
            Double calificacion) {
        this.idPropuesta = idPropuesta;
        this.fechaInicio = fechaInicio;
        this.monto = monto;
        this.estado = estado;
        this.calificacion = calificacion;
    }

    public int getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(int idPropuesta) {
        this.idPropuesta = idPropuesta;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
}
