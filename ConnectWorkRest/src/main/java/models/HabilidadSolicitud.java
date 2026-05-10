package models;

public class HabilidadSolicitud {
    private int id;
    private String nombre;
    private String descripcion;
    private int idCategoria;
    private int idFreelancer;
    private boolean aceptada;
    private boolean revisada;

    public HabilidadSolicitud() {
    }

    public HabilidadSolicitud(int id, String nombre, String descripcion, int idCategoria, int idFreelancer,
            boolean aceptada, boolean revisada) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.idFreelancer = idFreelancer;
        this.aceptada = aceptada;
        this.revisada = revisada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdFreelancer() {
        return idFreelancer;
    }

    public void setIdFreelancer(int idFreelancer) {
        this.idFreelancer = idFreelancer;
    }

    public boolean isAceptada() {
        return aceptada;
    }

    public void setAceptada(boolean aceptada) {
        this.aceptada = aceptada;
    }

    public boolean isRevisada() {
        return revisada;
    }

    public void setRevisada(boolean revisada) {
        this.revisada = revisada;
    }

}
