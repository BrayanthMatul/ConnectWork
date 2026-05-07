import { Perfil } from './perfil';

export interface Freelancer {
  idFreelancer: number;
  biografia: string;
  nivelExperiencia: string;
  calificacion: number;
  tarifaHora: number;
  perfil: Perfil;
}
