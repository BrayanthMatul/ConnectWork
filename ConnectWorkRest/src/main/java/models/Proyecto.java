package models;

import java.math.BigDecimal;

public class Proyecto {
    private int id;
    private int id_cliente;
    private int id_categoria;
    private String titulo;
    private String descripcion;
    private BigDecimal presupuesto_maximo;
    private String fecha_limite;
    private String estado;

    public Proyecto(int id, int id_cliente, int id_categoria, String titulo, String descripcion, BigDecimal presupuesto_maximo, String fecha_limite, String estado) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_categoria = id_categoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.presupuesto_maximo = presupuesto_maximo;
        this.fecha_limite = fecha_limite;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPresupuesto_maximo() {
        return presupuesto_maximo;
    }

    public String getFecha_limite() {
        return fecha_limite;
    }

    public String getEstado() {
        return estado;
    }   
}
