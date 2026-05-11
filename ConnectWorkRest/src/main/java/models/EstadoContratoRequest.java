package models;

import enums.EstadoContrato;

public class EstadoContratoRequest {
    private int idContrato;
    private EstadoContrato nuevoEstado;

    public EstadoContratoRequest(int idContrato, EstadoContrato nuevoEstado) {
        this.idContrato = idContrato;
        this.nuevoEstado = nuevoEstado;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public EstadoContrato getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(EstadoContrato nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}
