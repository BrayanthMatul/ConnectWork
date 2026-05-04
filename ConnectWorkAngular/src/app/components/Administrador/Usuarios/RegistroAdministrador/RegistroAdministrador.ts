import { Component, inject, signal } from '@angular/core';
import { Usuario } from '../../../../models/usuario';
import { TipoUsuario } from '../../../../enums/tipo-usuario';
import { UsuarioServicio } from '../../../../services/UsuarioServicio';
import { ModalService } from '../../../../services/ModalService';

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
  private usuarioServicio = inject(UsuarioServicio);
  private modalService = inject(ModalService);

  protected registrarUsuario() {
    const nuevoAdministrador: Usuario = {
      id: 0, // El ID se asignará en el backend
      username: this.username(),
      password: this.password(),
      nombreCompleto: this.nombreCompleto(),
      email: this.email(),
      tipoUsuario: TipoUsuario.ADMINISTRADOR,
    };

    this.usuarioServicio.registrarUsuario(nuevoAdministrador).subscribe({
      next: (respuesta) => {
        this.modalService.abrirExito(respuesta.valor);
        this.limpiarFormulario();
      },
      error: (error) => {
        console.error('Error al registrar el usuario:', error);
        const mensaje = error.error.valor || 'Error al registrar el usuario';
        this.modalService.abrirError(mensaje);
      },
    });
  }

  limpiarFormulario() {
    this.username.set('');
    this.password.set('');
    this.nombreCompleto.set('');
    this.email.set('');
  }
}
