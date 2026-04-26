package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistorialPorcentajeComision {
    
    private int id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private BigDecimal porcentajeComision;

    public HistorialPorcentajeComision(int id, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, BigDecimal porcentajeComision) {
        this.id = id;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.porcentajeComision = porcentajeComision;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public BigDecimal getPorcentajeComision() {
        return porcentajeComision;
    }
}
