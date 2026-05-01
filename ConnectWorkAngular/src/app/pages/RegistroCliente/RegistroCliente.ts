import { Component, inject, signal } from '@angular/core';
import { TipoUsuario } from '../../enums/tipo-usuario';
import { Perfil } from '../../models/perfil';
import { FormularioRegistro } from '../../shared/FormularioRegistro/FormularioRegistro';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro-cliente',
  imports: [FormularioRegistro],
  templateUrl: './RegistroCliente.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class RegistroCliente {
  tipoCliente = TipoUsuario.CLIENTE;
  tituloFormulario = 'Registro de Cliente';
  routeServicio = inject(Router);

  irInicio() {
    this.routeServicio.navigate(['/inicio']);
  }
}
