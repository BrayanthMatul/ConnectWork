package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import enums.EstadoPropuesta;

public class Propuesta {
    private int id;
    private int id_proyecto;
    private int id_freelancer;
    private BigDecimal monto_ofertado;
    private String mensaje;
    private LocalDateTime fecha_hora;
    private int plazoEntrega;
    private EstadoPropuesta estado;

    public Propuesta(int id, int id_proyecto, int id_freelancer, BigDecimal monto_ofertado, String mensaje, LocalDateTime fecha_hora, int plazoEntrega, EstadoPropuesta estado) {
        this.id = id;
        this.id_proyecto = id_proyecto;
        this.id_freelancer = id_freelancer;
        this.monto_ofertado = monto_ofertado;
        this.mensaje = mensaje;
        this.fecha_hora = fecha_hora;
        this.plazoEntrega = plazoEntrega;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public int getId_freelancer() {
        return id_freelancer;
    }

    public BigDecimal getMonto_ofertado() {
        return monto_ofertado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public int getPlazoEntrega() {
        return plazoEntrega;
    }

    public EstadoPropuesta getEstado() {
        return estado;
    }
    
}

