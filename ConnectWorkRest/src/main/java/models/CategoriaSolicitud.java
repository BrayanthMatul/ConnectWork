package models;

public class CategoriaSolicitud {

    private int id;
    private String nombre;
    private String descripcion;
    private boolean aceptada;
    private boolean revisada;
    private int idCliente;

    public CategoriaSolicitud(int id, String nombre, String descripcion, boolean aceptada, boolean revisada,
            int idCliente) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.aceptada = aceptada;
        this.revisada = revisada;
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isAceptada() {
        return aceptada;
    }

    public boolean isRevisada() {
        return revisada;
    }

    public int getIdCliente() {
        return idCliente;
    }

}
