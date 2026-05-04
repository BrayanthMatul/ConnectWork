import { Component, signal } from '@angular/core';

@Component({
  selector: 'app-registro-administrador',
  imports: [],
  templateUrl: './RegistroAdministrador.html',
})
export default class RegistroAdministrador {
  protected username = signal<string>('');
  protected password = signal<string>('');
  protected nombreCompleto = signal<string>('');
  protected email = signal<string>('');

  protected registrarUsuario() {}
}
