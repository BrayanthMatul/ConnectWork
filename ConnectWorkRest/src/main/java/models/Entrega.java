package models;

import java.time.LocalDateTime;

import enums.EstadoContrato;

public class Entrega {

    private int id;
    private int id_contrato;
    private String descripcion;
    private String archivo_url;
    private LocalDateTime fecha_hora;
    private EstadoContrato estado;
    private String motivo_rechazo;

    public Entrega(int id, int id_contrato, String descripcion, String archivo_url, LocalDateTime fecha_hora, EstadoContrato estado, String motivo_rechazo) {
        this.id = id;
        this.id_contrato = id_contrato;
        this.descripcion = descripcion;
        this.archivo_url = archivo_url;
        this.fecha_hora = fecha_hora;
        this.estado = estado;
        this.motivo_rechazo = motivo_rechazo;
    }

    public int getId() {
        return id;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getArchivo_url() {
        return archivo_url;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public String getMotivo_rechazo() {
        return motivo_rechazo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setArchivo_url(String archivo_url) {
        this.archivo_url = archivo_url;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public void setMotivo_rechazo(String motivo_rechazo) {
        this.motivo_rechazo = motivo_rechazo;
    }
    
}