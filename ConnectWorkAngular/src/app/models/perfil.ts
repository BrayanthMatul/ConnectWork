import { Usuario } from './usuario';

export interface Perfil {
  idPerfil: number;
  cui: string;
  telefono: string;
  fechaNacimiento: string;
  direccion: string;
  saldo: number;
  activo: boolean;
  perfilCompleto: boolean;
  usuario: Usuario;
}
