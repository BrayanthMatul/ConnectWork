import { Component, inject } from '@angular/core';
import { TipoUsuario } from '../../enums/tipo-usuario';
import { Perfil } from '../../models/perfil';
import { FormularioRegistro } from '../../shared/FormularioRegistro/FormularioRegistro';
import { Router } from '@angular/router';
import { Logo } from '../../shared/Logo/Logo';

@Component({
  selector: 'app-registro-freelancer',
  imports: [FormularioRegistro, Logo],
  templateUrl: './RegistroFreelancer.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class RegistroFreelancer {
  tipoFreelancer = TipoUsuario.FREELANCER;
  tituloFormulario = 'Registro de Freelancer';
}
