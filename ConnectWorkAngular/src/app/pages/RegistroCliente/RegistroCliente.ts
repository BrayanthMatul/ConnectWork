import { Component, inject, signal } from '@angular/core';
import { TipoUsuario } from '../../enums/tipo-usuario';
import { FormularioRegistro } from '../../shared/FormularioRegistro/FormularioRegistro';
import { Logo } from '../../shared/Logo/Logo';

@Component({
  selector: 'app-registro-cliente',
  imports: [FormularioRegistro, Logo],
  templateUrl: './RegistroCliente.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class RegistroCliente {
  tipoCliente = TipoUsuario.CLIENTE;
  tituloFormulario = 'Registro de Cliente';
}
