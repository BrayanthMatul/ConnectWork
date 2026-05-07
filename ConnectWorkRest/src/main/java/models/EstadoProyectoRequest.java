package models;

import enums.EstadoProyecto;

public class EstadoProyectoRequest {
    private int idProyecto;
    private EstadoProyecto nuevoEstado;

    public EstadoProyectoRequest() {
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public EstadoProyecto getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(EstadoProyecto nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}
