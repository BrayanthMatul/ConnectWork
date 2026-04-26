package models;

public class ProyectoHabilidad {
    
    private int id_proyecto;
    private int id_habilidad;

    public ProyectoHabilidad(int id_proyecto, int id_habilidad) {
        this.id_proyecto = id_proyecto;
        this.id_habilidad = id_habilidad;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public int getId_habilidad() {
        return id_habilidad;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public void setId_habilidad(int id_habilidad) {
        this.id_habilidad = id_habilidad;
    }
}
