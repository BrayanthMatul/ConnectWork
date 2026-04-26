import { EstadoPropuesta } from '../enums/estado-presupuesto';

export interface Propuesta {
  id: number;
  idProyecto: number;
  idFreelancer: number;
  montoOfertado: number;
  mensaje: string;
  fechaHora: Date;
  plazoEntrega: number;
  estado: EstadoPropuesta;
}
