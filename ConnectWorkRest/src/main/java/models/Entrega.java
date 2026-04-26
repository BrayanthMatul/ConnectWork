package models;

import java.time.LocalDateTime;

import enums.EstadoContrato;

public class Entrega {

    private int id;
    private int idContrato;
    private String descripcion;
    private String archivoUrl;
    private LocalDateTime fechaHora;
    private EstadoContrato estado;
    private String motivoRechazo;

    public Entrega(int id, int idContrato, String descripcion, String archivoUrl, LocalDateTime fechaHora, EstadoContrato estado, String motivoRechazo) {
        this.id = id;
        this.idContrato = idContrato;
        this.descripcion = descripcion;
        this.archivoUrl = archivoUrl;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.motivoRechazo = motivoRechazo;
    }

    public int getId() {
        return id;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getArchivoUrl() {
        return archivoUrl;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }
    
}