import { EstadoContrato } from "../enums/estado-contrato";

export interface Contrato {
    id: number;
    idPropuesta: number;
    fechaInicio: Date;
    monto: number;
    estado: EstadoContrato;
    calificacion: number;
}
