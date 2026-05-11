package models;

import java.time.LocalDateTime;

public class Entrega {

    private int id;
    private int idContrato;
    private String descripcion;
    private String archivoUrl;
    private LocalDateTime fechaHora;
    private boolean aceptado;
    private boolean revisado;
    private String motivoRechazo;

    public Entrega(int id, int idContrato, String descripcion, String archivoUrl, LocalDateTime fechaHora,
            boolean aceptado, boolean revisado, String motivoRechazo) {
        this.id = id;
        this.idContrato = idContrato;
        this.descripcion = descripcion;
        this.archivoUrl = archivoUrl;
        this.fechaHora = fechaHora;
        this.aceptado = aceptado;
        this.revisado = revisado;
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

    public boolean isAceptado() {
        return aceptado;
    }

    public boolean isRevisado() {
        return revisado;
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

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public void setRevisado(boolean revisado) {
        this.revisado = revisado;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

}