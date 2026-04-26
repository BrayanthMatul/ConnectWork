import { EstadoContrato } from "../enums/estado-contrato";

export interface Entrega {
    id: number;
    idContrato: number;
    descripcion: string;
    archivoUrl: string;
    fechaHora: Date;
    estado: EstadoContrato;
    motivoRechazo: string;
}
