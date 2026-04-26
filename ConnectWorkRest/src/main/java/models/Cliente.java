package models;

public class Cliente {
    private int id_cliente;
    private String descripcion;
    private String sector;
    private String sitio_web;
    private Perfil perfil;

    public Cliente(int id_cliente, String descripcion, String sector, String sitio_web, Perfil perfil) {
        this.id_cliente = id_cliente;
        this.descripcion = descripcion;
        this.sector = sector;
        this.sitio_web = sitio_web;
        this.perfil = perfil;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSector() {
        return sector;
    }

    public String getSitio_web() {
        return sitio_web;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setSitio_web(String sitio_web) {
        this.sitio_web = sitio_web;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    
}
