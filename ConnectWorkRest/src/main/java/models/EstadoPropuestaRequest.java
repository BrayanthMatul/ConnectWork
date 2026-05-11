package models;

import enums.EstadoPropuesta;

public class EstadoPropuestaRequest {
    private int idPropuesta;
    private EstadoPropuesta nuevoEstado;

    EstadoPropuestaRequest(int idPropuesta, EstadoPropuesta nuevoEstado) {
        this.idPropuesta = idPropuesta;
        this.nuevoEstado = nuevoEstado;
    }

    public int getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(int idPropuesta) {
        this.idPropuesta = idPropuesta;
    }

    public EstadoPropuesta getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(EstadoPropuesta nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }

}
