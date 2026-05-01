import { Routes } from '@angular/router';
import { Inicio } from './pages/Inicio/Inicio';

export const routes: Routes = [
  {
    path: '',
    component: Inicio,
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
    redirectTo: '',
  },
];
