import { Perfil } from "./perfil";

export interface Freelancer {
    idFreelancer: number;
    biografia: string;
    calificacion: number;
    tarifaHora: number;
    perfil: Perfil;
}
