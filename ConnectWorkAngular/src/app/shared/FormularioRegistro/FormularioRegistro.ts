import { Component, inject, input, signal } from '@angular/core';
import { Perfil } from '../../models/perfil';
import { Usuario } from '../../models/usuario';
import { TipoUsuario } from '../../enums/tipo-usuario';
import { PerfilServicio } from '../../services/PerfilServicio';
import { ModalService } from '../../services/ModalService';
import { AuthService } from '../../services/AuthServicio';
import { LoginRequest } from '../../services/LoginServicio';

@Component({
  selector: 'app-formulario-registro',
  templateUrl: './FormularioRegistro.html',
})
export class FormularioRegistro {
  titulo = input.required<string>();
  tipoUsuarioEnviado = input.required<TipoUsuario>();
  perfilServicio = inject(PerfilServicio);
  private authService = inject(AuthService);
  private modalService = inject(ModalService);

  // Signals para los campos de usuario
  username = signal<string>('');
  password = signal<string>('');
  nombreCompleto = signal<string>('');
  email = signal<string>('');

  // Signals para los campos de perfil
  cui = signal<string>('');
  telefono = signal<string>('');
  fechaNacimientoIngresado = signal<string>('');
  direccion = signal<string>('');

  protected onSubmit() {
    // Construir el objeto Usuario
    const usuario: Usuario = {
      id: 0, // Se ignora, lo asigna backend
      username: this.username(),
      password: this.password(),
      nombreCompleto: this.nombreCompleto(),
      email: this.email(),
      tipoUsuario: this.tipoUsuarioEnviado(),
    };
    // Construir el objeto Perfil
    const perfil: Perfil = {
      idPerfil: 0,
      cui: this.cui(),
      telefono: this.telefono(),
      fechaNacimiento: this.fechaNacimientoIngresado(),
      direccion: this.direccion(),
      saldo: 0,
      activo: true,
      perfilCompleto: false,
      usuario,
    };
    // Registrar el perfil
    this.perfilServicio.registrarPerfil(perfil).subscribe({
      next: () => {
        // Realizar login automático después del registro
        const loginRequest: LoginRequest = {
          usernameOrEmail: this.username(),
          password: this.password(),
        };
        this.authService.login(loginRequest).subscribe({
          error: (error) => {
            console.log('Error al hacer login automático:', error);
            const mensaje = error.error?.valor || 'Error al iniciar sesión';
            this.modalService.abrirError(mensaje);
          },
        });
      },
      error: (error) => {
        // Captura el mensaje exacto del backend
        console.log('Error al registrar perfil:', error);
        const mensaje = error.error.valor || 'Error al registrar';
        this.modalService.abrirError(mensaje);
      },
    });
  }
}
