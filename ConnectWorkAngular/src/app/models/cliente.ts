import { Perfil } from './perfil';

export interface Cliente {
  idCliente: number;
  descripcion: string;
  sector: string;
  sitioWeb: string;
  perfil: Perfil;
}
