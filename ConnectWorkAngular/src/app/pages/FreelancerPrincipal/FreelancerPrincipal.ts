import { Component } from '@angular/core';
import { MenuOpciones } from '../../shared/Menu/Menu';
import { Header } from '../../shared/Header/Header';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-freelancer-principal',
  imports: [Header, RouterOutlet],
  templateUrl: './FreelancerPrincipal.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class FreelancerPrincipal {
  protected opcionesMenu = <MenuOpciones[]>[
    {
      etiqueta: 'Explorar Proyectos',
      ruta: 'explorar-proyectos',
    },

    {
      etiqueta: 'Envios',
      subOpciones: [
        { etiqueta: 'Propuestas pendientes', ruta: 'propuestas-pendientes' },
        { etiqueta: 'Propuestas aceptadas', ruta: 'propuestas-aceptadas' },
        { etiqueta: 'Propuestas rechazadas', ruta: 'propuestas-rechazadas' },
        { etiqueta: 'Entregas enviadas', ruta: 'contratos-activos/pendientes' },
      ],
    },

    { etiqueta: 'Contratos activos', ruta: 'contratos-activos' },

    { etiqueta: 'Solicitar Habilidad', ruta: 'solicitar-habilidad' },
    { etiqueta: 'Saldo', ruta: 'saldo' },
    { etiqueta: 'Reportes', ruta: 'reportes' },
  ];

  protected opcionesMenuPerfil = <MenuOpciones[]>[
    { etiqueta: 'Mi Perfil', ruta: '/' },
    { etiqueta: 'Editar Perfil', ruta: 'editar-perfil' },
  ];
}
