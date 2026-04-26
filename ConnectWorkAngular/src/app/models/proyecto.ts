import { EstadoProyecto } from "../enums/estado-proyecto";

export interface Proyecto {
    id: number;
    idCliente: number;
    idCategoria: number;
    titulo: string;
    descripcion: string;
    presupuestoMaximo: number;
    fechaLimite: Date;
    estado: EstadoProyecto;
}
