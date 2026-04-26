export interface Perfil {
    id_perfil: number;
    cui: string;
    telefono: string;
    fecha_nacimiento: Date;
    direccion: string;
    saldo: number;
    activo: boolean;
    perfil_completo: boolean;
    usuario: Usuario;
}
