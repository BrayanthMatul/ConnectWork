package models;

import java.math.BigDecimal;

public class Freelancer{
    private int idFreelancer;
    private String biografia;
    private float calificacion;
    private BigDecimal tarifaHora;
    private Perfil perfil;
    
    public Freelancer(int idFreelancer, String biografia, float calificacion, BigDecimal tarifaHora, Perfil perfil) {
        this.idFreelancer = idFreelancer;
        this.biografia = biografia;
        this.calificacion = calificacion;
        this.tarifaHora = tarifaHora;
        this.perfil = perfil;
    }

    public int getIdFreelancer() {
        return idFreelancer;
    }

    public String getBiografia() {
        return biografia;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public BigDecimal getTarifaHora() {
        return tarifaHora;
    }

    public Perfil getPerfil() {
        return perfil;
    }
    public void setIdFreelancer(int idFreelancer) {
        this.idFreelancer = idFreelancer;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public void setTarifaHora(BigDecimal tarifaHora) {
        this.tarifaHora = tarifaHora;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

}
