import { TipoUsuario } from "../enums/tipo-usuario";

export interface Usuario {
    id: number;
    username: string;
    password: string;
    nombreCompleto: string;
    email: string;
    tipoUsuario: TipoUsuario;
}
