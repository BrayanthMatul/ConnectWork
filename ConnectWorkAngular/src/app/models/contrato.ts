export interface Contrato {
    id: number;
    id_propuesta: number;
    fecha_inicio: Date;
    monto: number;
    estado: EstadoContrato;
    calificacion: number;
}
