import { Component } from '@angular/core';
import { MenuOpciones } from '../../shared/Menu/Menu';
import { Header } from '../../shared/Header/Header';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-cliente-principal',
  imports: [Header, RouterOutlet],
  templateUrl: './ClientePrincipal.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class ClientePrincipal {
  protected opcionesMenu = <MenuOpciones[]>[
    {
      etiqueta: 'Proyectos',
      subOpciones: [
        { etiqueta: 'Publicar Proyecto', ruta: 'publicar-proyecto' },
        { etiqueta: 'Cancelar Proyecto', ruta: 'cancelar-proyecto' },
        { etiqueta: 'Proyectos Publicados', ruta: 'proyectos-publicados' },
      ],
    },

    {
      etiqueta: 'Buzon de entrada',
      subOpciones: [
        { etiqueta: 'Propuestas Recibidas', ruta: 'propuestas-recibidas' },
        { etiqueta: 'Entregas Recibidas', ruta: 'entregas-recibidas' },
      ],
    },

    {
      etiqueta: 'Finanzas',
      subOpciones: [
        { etiqueta: 'Saldo', ruta: 'saldo' },
        { etiqueta: 'Recargar Saldo', ruta: 'recargar-saldo' },
      ],
    },
    { etiqueta: 'Solicitar categoria', ruta: 'solicitar-categoria' },
    { etiqueta: 'Reportes', ruta: 'reportes' },
  ];

  protected opcionesMenuPerfil = <MenuOpciones[]>[
    { etiqueta: 'Mi Perfil', ruta: '/' },
    { etiqueta: 'Editar Perfil', ruta: 'editar-perfil' },
  ];
}
