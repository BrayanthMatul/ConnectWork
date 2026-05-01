import { Component, inject } from '@angular/core';
import { LoginForm } from '../../components/LoginForm/LoginForm';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inicio',
  imports: [LoginForm],
  templateUrl: './Inicio.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export class Inicio {
  routeServicio = inject(Router);

  irRegistroCliente() {
    this.routeServicio.navigate(['/registro-cliente']);
  }

  irRegistroFreelancer() {
    this.routeServicio.navigate(['/registro-freelancer']);
  }
}
