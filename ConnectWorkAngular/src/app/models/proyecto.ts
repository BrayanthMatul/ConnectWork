import { EstadoProyecto } from '../enums/estado-proyecto';
import { ProyectoHabilidad } from './proyecto-habilidad';

export interface Proyecto {
  id: number;
  idCliente: number;
  idCategoria: number;
  titulo: string;
  descripcion: string;
  presupuestoMaximo: number;
  fechaLimite: string;
  estado: EstadoProyecto;
  habilidades?: ProyectoHabilidad[];
}
