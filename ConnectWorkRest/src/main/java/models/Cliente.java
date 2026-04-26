package models;

public class Cliente {
    private int idCliente;
    private String descripcion;
    private String sector;
    private String sitioWeb;
    private Perfil perfil;

    public Cliente(int idCliente, String descripcion, String sector, String sitioWeb, Perfil perfil) {
        this.idCliente = idCliente;
        this.descripcion = descripcion;
        this.sector = sector;
        this.sitioWeb = sitioWeb;
        this.perfil = perfil;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSector() {
        return sector;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    
}
