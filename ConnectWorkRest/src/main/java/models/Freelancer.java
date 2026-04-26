package models;

import java.math.BigDecimal;

public class Freelancer{
    private int id_freelancer;
    private String biografia;
    private float calificacion;
    private BigDecimal tarifa_hora;
    private Perfil perfil;
    
    public Freelancer(int id_freelancer, String biografia, float calificacion, BigDecimal tarifa_hora, Perfil perfil) {
        this.id_freelancer = id_freelancer;
        this.biografia = biografia;
        this.calificacion = calificacion;
        this.tarifa_hora = tarifa_hora;
        this.perfil = perfil;
    }

    public int getId_freelancer() {
        return id_freelancer;
    }

    public String getBiografia() {
        return biografia;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public BigDecimal getTarifa_hora() {
        return tarifa_hora;
    }

    public Perfil getPerfil() {
        return perfil;
    }
    public void setId_freelancer(int id_freelancer) {
        this.id_freelancer = id_freelancer;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public void setTarifa_hora(BigDecimal tarifa_hora) {
        this.tarifa_hora = tarifa_hora;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

}
