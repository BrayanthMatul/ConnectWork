package models;

import java.math.BigDecimal;
import java.sql.Date;

import enums.EstadoProyecto;

public class Proyecto {
    private int id;
    private int idCliente;
    private int idCategoria;
    private String titulo;
    private String descripcion;
    private BigDecimal presupuestoMaximo;
    private Date fechaLimite;
    private EstadoProyecto estado;

    public Proyecto(int id, int idCliente, int idCategoria, String titulo, String descripcion, BigDecimal presupuestoMaximo, Date fechaLimite, EstadoProyecto estado) {
        this.id = id;
        this.idCliente = idCliente;
        this.idCategoria = idCategoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.presupuestoMaximo = presupuestoMaximo;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPresupuestoMaximo() {
        return presupuestoMaximo;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public EstadoProyecto getEstado() {
        return estado;
    }   
}
