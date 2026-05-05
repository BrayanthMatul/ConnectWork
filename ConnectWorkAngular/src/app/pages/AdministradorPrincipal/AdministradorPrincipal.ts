import { Component } from '@angular/core';
import { MenuOpciones, Menu } from '../../shared/Menu/Menu';
import { Header } from '../../shared/Header/Header';
import { RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-administrador-principal',
  imports: [Header, RouterOutlet],
  templateUrl: './AdministradorPrincipal.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class AdministradorPrincipal {
  protected opcionesMenu = <MenuOpciones[]>[
    {
      etiqueta: 'Usuarios',
      subOpciones: [
        { etiqueta: 'Registrar administrador', ruta: 'registro-administrador' },
        { etiqueta: 'Activar/Desactivar Usuario', ruta: 'activar-desactivar-usuario' },
        { etiqueta: 'Lista de Administradores', ruta: 'lista-administradores' },
        { etiqueta: 'Lista de Clientes', ruta: 'lista-clientes' },
        { etiqueta: 'Lista de Freelancers', ruta: 'lista-freelancers' },
      ],
    },
    {
      etiqueta: 'Categoria',
      subOpciones: [
        { etiqueta: 'Registrar Categoria', ruta: 'registro-categoria' },
        { etiqueta: 'Editar Categoria', ruta: 'editar-categoria' },
        { etiqueta: 'Activar/Desactivar Categoria', ruta: 'activar-desactivar-categoria' },
        { etiqueta: 'Lista de Categorias', ruta: 'lista-categorias' },
        { etiqueta: 'Registrar Habilidad', ruta: 'registro-habilidad' },
        { etiqueta: 'Editar Habilidad', ruta: 'editar-habilidad' },
        { etiqueta: 'Activar/Desactivar Habilidad', ruta: 'activar-desactivar-habilidad' },
        { etiqueta: 'Lista de Habilidades', ruta: 'lista-habilidades' },
        { etiqueta: 'Solicitudes', ruta: 'solicitudes' },
      ],
    },

    {
      etiqueta: 'Finanzas',
      subOpciones: [
        { etiqueta: 'Saldo global', ruta: 'saldo-global' },
        { etiqueta: 'Cambio de porcentaje', ruta: 'cambio-porcentaje' },
      ],
    },
    { etiqueta: 'Reportes', ruta: 'reportes' },
    { etiqueta: 'Cargar Archivos', ruta: 'carga-archivo' },
  ];

  protected opcionesMenuPerfil = <MenuOpciones[]>[
    { etiqueta: 'Mi Perfil', ruta: '/' },
    { etiqueta: 'Editar Perfil', ruta: 'editar-perfil' },
    { etiqueta: 'Cerrar Sesión', ruta: 'cerrar-sesion' },
  ];
}
