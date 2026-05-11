import { EstadoContrato } from '../enums/estado-contrato';

export interface Contrato {
  id: number;
  idPropuesta: number;
  fechaInicio: string;
  monto: number;
  estado: EstadoContrato;
  calificacion: number;
}
