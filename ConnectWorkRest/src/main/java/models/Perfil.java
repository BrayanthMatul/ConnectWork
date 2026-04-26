package models;

import java.math.BigDecimal;
import java.sql.Date;

public class Perfil{
    
    private int id_perfil;
    private String cui;
    private String telefono;
    private Date fecha_nacimiento;
    private String direccion;
    private BigDecimal saldo;
    private boolean activo;
    private boolean perfil_completo;
    private Usuario usuario;

    public Perfil(int id_perfil, String cui, String telefono, Date fecha_nacimiento, String direccion, BigDecimal saldo, boolean activo, boolean perfil_completo, Usuario usuario) {
        this.id_perfil = id_perfil;
        this.cui = cui;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.direccion = direccion;
        this.saldo = saldo;
        this.activo = activo;
        this.perfil_completo = perfil_completo;
        this.usuario = usuario;
    }


    public int getId_perfil() {
        return id_perfil;
    }

    public String getCui() {
        return cui;
    }

    public String getTelefono() {
        return telefono;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean isPerfil_completo() {
        return perfil_completo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId_perfil(int id_perfil) {
        this.id_perfil = id_perfil;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setPerfil_completo(boolean perfil_completo) {
        this.perfil_completo = perfil_completo;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
