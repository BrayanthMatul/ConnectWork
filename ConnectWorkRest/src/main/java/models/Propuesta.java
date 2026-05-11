package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import enums.EstadoPropuesta;

public class Propuesta {
    private int id;
    private int idProyecto;
    private int idFreelancer;
    private BigDecimal montoOfertado;
    private String mensaje;
    private LocalDateTime fechaHora;
    private int plazoEntrega;
    private EstadoPropuesta estado;

    public Propuesta(int id, int idProyecto, int idFreelancer, BigDecimal montoOfertado, String mensaje,
            int plazoEntrega, EstadoPropuesta estado) {
        this.id = id;
        this.idProyecto = idProyecto;
        this.idFreelancer = idFreelancer;
        this.montoOfertado = montoOfertado;
        this.mensaje = mensaje;
        this.plazoEntrega = plazoEntrega;
        this.estado = estado;
    }

    public Propuesta(int id, int idProyecto, int idFreelancer, BigDecimal montoOfertado, String mensaje,
            int plazoEntrega, EstadoPropuesta estado, LocalDateTime fechaHora) {
        this.id = id;
        this.idProyecto = idProyecto;
        this.idFreelancer = idFreelancer;
        this.montoOfertado = montoOfertado;
        this.mensaje = mensaje;
        this.plazoEntrega = plazoEntrega;
        this.estado = estado;
        this.fechaHora = fechaHora;
    }

    public int getId() {
        return id;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public int getIdFreelancer() {
        return idFreelancer;
    }

    public BigDecimal getMontoOfertado() {
        return montoOfertado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public int getPlazoEntrega() {
        return plazoEntrega;
    }

    public EstadoPropuesta getEstado() {
        return estado;
    }

}
