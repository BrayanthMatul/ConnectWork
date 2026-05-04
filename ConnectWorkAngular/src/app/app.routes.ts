import { Routes } from '@angular/router';
import { Inicio } from './pages/Inicio/Inicio';

export const routes: Routes = [
  {
    path: 'Inicio',
    component: Inicio,
  },

  {
    path: 'administrador-principal',
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
        path: 'desactivar-habilidad',
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

      { path: '**', redirectTo: 'registro-administrador' },
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
    path: '**',
    redirectTo: 'administrador-principal',
  },
];
