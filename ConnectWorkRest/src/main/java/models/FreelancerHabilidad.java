package models;

public class FreelancerHabilidad {
    
    private int id_freelancer;
    private int id_habilidad;

    public FreelancerHabilidad(int id_freelancer, int id_habilidad) {
        this.id_freelancer = id_freelancer;
        this.id_habilidad = id_habilidad;
    }

    public int getId_freelancer() {
        return id_freelancer;
    }

    public int getId_habilidad() {
        return id_habilidad;
    }

    public void setId_freelancer(int id_freelancer) {
        this.id_freelancer = id_freelancer;
    }

    public void setId_habilidad(int id_habilidad) {
        this.id_habilidad = id_habilidad;
    }
}
