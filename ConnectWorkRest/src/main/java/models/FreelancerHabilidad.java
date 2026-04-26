package models;

public class FreelancerHabilidad {
    
    private int idFreelancer;
    private int idHabilidad;

    public FreelancerHabilidad(int idFreelancer, int idHabilidad) {
        this.idFreelancer = idFreelancer;
        this.idHabilidad = idHabilidad;
    }

    public int getIdFreelancer() {
        return idFreelancer;
    }

    public int getIdHabilidad() {
        return idHabilidad;
    }

    public void setIdFreelancer(int idFreelancer) {
        this.idFreelancer = idFreelancer;
    }

    public void setIdHabilidad(int idHabilidad) {
        this.idHabilidad = idHabilidad;
    }
}
