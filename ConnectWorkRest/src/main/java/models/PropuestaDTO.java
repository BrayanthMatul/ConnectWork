package models;

import java.math.BigDecimal;

import enums.EstadoPropuesta;

public class PropuestaDTO {
    private int idProyecto;
    private int idFreelancer;
    private BigDecimal montoOfertado;
    private String mensaje;
    private int plazoEntrega;
    private EstadoPropuesta estado;

    public PropuestaDTO() {
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdFreelancer() {
        return idFreelancer;
    }

    public void setIdFreelancer(int idFreelancer) {
        this.idFreelancer = idFreelancer;
    }

    public java.math.BigDecimal getMontoOfertado() {
        return montoOfertado;
    }

    public void setMontoOfertado(java.math.BigDecimal montoOfertado) {
        this.montoOfertado = montoOfertado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getPlazoEntrega() {
        return plazoEntrega;
    }

    public void setPlazoEntrega(int plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
    }

    public EstadoPropuesta getEstado() {
        return estado;
    }

    public void setEstado(EstadoPropuesta estado) {
        this.estado = estado;
    }
}
