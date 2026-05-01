package models;

import java.math.BigDecimal;
import java.sql.Date;

public class Perfil {

    private int idPerfil;
    private String cui;
    private String telefono;
    private Date fechaNacimiento;
    private String direccion;
    private BigDecimal saldo;
    private boolean activo;
    private boolean perfilCompleto;
    private Usuario usuario;

    public Perfil(int idPerfil, String cui, String telefono, Date fechaNacimiento, String direccion, BigDecimal saldo,
            boolean activo, boolean perfilCompleto, Usuario usuario) {
        this.idPerfil = idPerfil;
        this.cui = cui;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.saldo = saldo;
        this.activo = activo;
        this.perfilCompleto = perfilCompleto;
        this.usuario = usuario;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public String getCui() {
        return cui;
    }

    public String getTelefono() {
        return telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
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

    public boolean isPerfilCompleto() {
        return perfilCompleto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public void setPerfilCompleto(boolean perfilCompleto) {
        this.perfilCompleto = perfilCompleto;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
