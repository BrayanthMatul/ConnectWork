import { Routes } from '@angular/router';
import { Inicio } from './pages/Inicio/Inicio';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: 'Inicio',
    component: Inicio,
  },

  {
    path: 'administrador-principal',
    canActivate: [AuthGuard],
    loadComponent: () => import('./pages/AdministradorPrincipal/AdministradorPrincipal'),
    children: [
      {
        path: 'registro-administrador',
        loadComponent: () =>
          import('./components/Administrador/Usuarios/RegistroAdministrador/RegistroAdministrador'),
      },

      {
        path: 'activar-desactivar-usuario',
        loadComponent: () =>
          import('./components/Administrador/Usuarios/ActivarDesactivarUsuarios/ActivarDesactivarUsuarios'),
      },

      {
        path: 'lista-administradores',
        loadComponent: () =>
          import('./components/Administrador/Usuarios/ListaAdministradores/ListaAdministradores'),
      },
      {
        path: 'lista-clientes',
        loadComponent: () =>
          import('./components/Administrador/Usuarios/ListadoClientes/ListadoClientes'),
      },
      {
        path: 'lista-freelancers',
        loadComponent: () =>
          import('./components/Administrador/Usuarios/ListadoFreelancers/ListadoFreelancers'),
      },

      {
        path: 'registro-categoria',
        loadComponent: () =>
          import('./components/Administrador/Categoria/RegistrarCategoria/RegistrarCategoria'),
      },

      {
        path: 'editar-categoria',
        loadComponent: () =>
          import('./components/Administrador/Categoria/EditarCategoria/EditarCategoria'),
      },
      {
        path: 'activar-desactivar-categoria',
        loadComponent: () =>
          import('./components/Administrador/Categoria/ActivarDesactivarCategoria/ActivarDesactivarCategoria'),
      },
      {
        path: 'lista-categorias',
        loadComponent: () =>
          import('./components/Administrador/Categoria/ListaDeCategorias/ListaDeCategorias'),
      },
      {
        path: 'registro-habilidad',
        loadComponent: () =>
          import('./components/Administrador/Categoria/RegistrarHabilidad/RegistrarHabilidad'),
      },
      {
        path: 'editar-habilidad',
        loadComponent: () =>
          import('./components/Administrador/Categoria/EditarHabilidad/EditarHabilidad'),
      },
      {
        path: 'activar-desactivar-habilidad',
        loadComponent: () =>
          import('./components/Administrador/Categoria/ActivarDesactivarHabilidad/ActivarDesactivarHabilidad'),
      },
      {
        path: 'solicitudes',
        loadComponent: () => import('./components/Administrador/Categoria/Solicitudes/Solicitudes'),
      },

      {
        path: 'saldo-global',
        loadComponent: () => import('./components/Administrador/Finanzas/SaldoGlobal/SaldoGlobal'),
      },
      {
        path: 'cambio-porcentaje',
        loadComponent: () =>
          import('./components/Administrador/Finanzas/CambioPorcentaje/CambioPorcentaje'),
      },

      {
        path: 'reportes',
        loadComponent: () => import('./components/Administrador/Reportes/Reportes'),
      },

      {
        path: 'carga-archivo',
        loadComponent: () => import('./components/Administrador/CargaAchivo/CargaAchivo'),
      },
      {
        path: 'lista-habilidades',
        loadComponent: () =>
          import('./components/Administrador/Categoria/ListaDeHabilidades/ListaDeHabilidades'),
      },

      { path: '**', redirectTo: 'registro-administrador' },
    ],
  },

  {
    path: 'cliente-principal',
    canActivate: [AuthGuard],
    loadComponent: () => import('./pages/ClientePrincipal/ClientePrincipal'),

    children: [
      {
        path: 'publicar-proyecto',
        loadComponent: () =>
          import('./components/Cliente/Proyectos/PublicarProyecto/PublicarProyecto'),
      },

      {
        path: 'cancelar-proyecto',
        loadComponent: () =>
          import('./components/Cliente/Proyectos/CancelarProyecto/CancelarProyecto'),
      },

      {
        path: 'proyectos-publicados',
        loadComponent: () =>
          import('./components/Cliente/Proyectos/ProyectosPublicados/ProyectosPublicados'),
      },

      {
        path: 'propuestas-recibidas',
        loadComponent: () => import('./components/Cliente/PropuestasRecibidas/PropuestasRecibidas'),
      },

      {
        path: 'entregas-recibidas',
        loadComponent: () => import('./components/Cliente/EntregasRecibidas/EntregasRecibidas'),
      },

      {
        path: 'saldo',
        loadComponent: () => import('./components/Cliente/Finanzas/Saldo/Saldo'),
      },

      {
        path: 'recargar-saldo',
        loadComponent: () => import('./components/Cliente/Finanzas/RecargarSaldo/RecargarSaldo'),
      },

      {
        path: 'reportes',
        loadComponent: () => import('./components/Cliente/ReportesCliente/ReportesCliente'),
      },

      {
        path: 'solicitar-categoria',
        loadComponent: () => import('./components/Cliente/SolicitarCategoria/SolicitarCategoria'),
      },
    ],
  },

  {
    path: 'freelancer-principal',
    canActivate: [AuthGuard],
    loadComponent: () => import('./pages/FreelancerPrincipal/FreelancerPrincipal'),

    children: [
      {
        path: 'explorar-proyectos',
        loadComponent: () => import('./components/Freelancer/ExplorarProyectos/ExplorarProyectos'),
      },
      {
        path: 'contratos-activos',
        loadComponent: () => import('./components/Freelancer/ContratosActivos/ContratosActivos'),
      },

      {
        path: 'solicitar-habilidad',
        loadComponent: () =>
          import('./components/Freelancer/SolicitarHabilidad/SolicitarHabilidad'),
      },

      {
        path: 'saldo',
        loadComponent: () => import('./components/Freelancer/Saldo/Saldo'),
      },
      {
        path: 'reportes',
        loadComponent: () =>
          import('./components/Freelancer/ReportesFreelancer/ReportesFreelancer'),
      },
    ],
  },
  {
    path: 'registro-cliente',
    loadComponent: () => import('./pages/RegistroCliente/RegistroCliente'),
  },

  {
    path: 'registro-freelancer',
    loadComponent: () => import('./pages/RegistroFreelancer/RegistroFreelancer'),
  },

  {
    path: 'datos-iniciales-cliente',
    canActivate: [AuthGuard],
    loadComponent: () => import('./pages/DatosInicialesCliente/DatosInicialesCliente'),
  },

  {
    path: 'datos-iniciales-freelancer',
    canActivate: [AuthGuard],
    loadComponent: () => import('./pages/DatosInicialesFreelancer/DatosInicialesFreelancer'),
  },

  {
    path: '**',
    redirectTo: 'Inicio',
  },
];
