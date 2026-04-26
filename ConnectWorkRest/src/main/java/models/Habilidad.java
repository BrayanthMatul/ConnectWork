package models;

public class Habilidad {

    private int id;
    private int idCategoria;
    private String nombre;
    private String descripcion;
    private boolean activo;

    public Habilidad(int id, int idCategoria, String nombre, String descripcion, boolean activo) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setId(int id) {
        this.id = id;
    }  

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
