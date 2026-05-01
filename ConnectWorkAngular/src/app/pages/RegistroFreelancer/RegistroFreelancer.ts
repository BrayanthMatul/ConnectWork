import { Component, inject } from '@angular/core';
import { TipoUsuario } from '../../enums/tipo-usuario';
import { Perfil } from '../../models/perfil';
import { FormularioRegistro } from '../../shared/FormularioRegistro/FormularioRegistro';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro-freelancer',
  imports: [FormularioRegistro],
  templateUrl: './RegistroFreelancer.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class RegistroFreelancer {
  tipoFreelancer = TipoUsuario.FREELANCER;
  tituloFormulario = 'Registro de Freelancer';
  routeServicio = inject(Router);

  irInicio() {
    this.routeServicio.navigate(['/inicio']);
  }
}
