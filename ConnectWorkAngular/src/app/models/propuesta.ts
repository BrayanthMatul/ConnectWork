import { EstadoPropuesta } from '../enums/estado-propuesta';

export interface Propuesta {
  id: number;
  idProyecto: number;
  idFreelancer: number;
  montoOfertado: number;
  mensaje: string;
  fechaHora: string;
  plazoEntrega: number;
  estado: EstadoPropuesta;
}
