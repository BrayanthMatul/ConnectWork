package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistorialPorcentajeComision {
    
    private int id;
    private LocalDateTime fecha_hora_inicio;
    private LocalDateTime fecha_hora_fin;
    private BigDecimal porcentaje_comision;

    public HistorialPorcentajeComision(int id, LocalDateTime fecha_hora_inicio, LocalDateTime fecha_hora_fin, BigDecimal porcentaje_comision) {
        this.id = id;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fecha_hora_fin = fecha_hora_fin;
        this.porcentaje_comision = porcentaje_comision;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getFecha_hora_inicio() {
        return fecha_hora_inicio;
    }

    public LocalDateTime getFecha_hora_fin() {
        return fecha_hora_fin;
    }

    public BigDecimal getPorcentaje_comision() {
        return porcentaje_comision;
    }
}
