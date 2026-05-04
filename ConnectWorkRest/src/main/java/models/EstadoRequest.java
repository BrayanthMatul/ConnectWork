package models;

public class EstadoRequest {
    private int idPerfil;
    private boolean activo;

    public EstadoRequest(int idPerfil, boolean activo) {
        this.idPerfil = idPerfil;
        this.activo = activo;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
