package models;

public class ProyectoHabilidad {
    
    private int idProyecto;
    private int idHabilidad;

    public ProyectoHabilidad(int idProyecto, int idHabilidad) {
        this.idProyecto = idProyecto;
        this.idHabilidad = idHabilidad;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public int getIdHabilidad() {
        return idHabilidad;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public void setIdHabilidad(int idHabilidad) {
        this.idHabilidad = idHabilidad;
    }
}
