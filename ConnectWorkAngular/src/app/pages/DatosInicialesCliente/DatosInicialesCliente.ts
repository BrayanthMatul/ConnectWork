import { Component, inject, input, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Logo } from '../../shared/Logo/Logo';
import { ClienteServicio } from '../../services/ClienteServicio';
import { LoginServicio } from '../../services/LoginServicio';
import { ModalService } from '../../services/ModalService';
import { Cliente } from '../../models/cliente';

@Component({
  selector: 'app-datos-iniciales-cliente',
  imports: [Logo],
  templateUrl: './DatosInicialesCliente.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class DatosInicialesCliente {
  protected id_cliente = input.required<number>();
  protected descripcion = signal<string>('');
  protected sector = signal<string>('');
  protected sitio_web = signal<string>('');

  private clienteServicio = inject(ClienteServicio);
  private loginServicio = inject(LoginServicio);
  private modalService = inject(ModalService);
  private router = inject(Router);

  protected onSubmit() {
    const usuario = this.loginServicio.getUsuario();
    if (!usuario) {
      this.modalService.abrirError('Usuario no encontrado');
      return;
    }

    const cliente: Cliente = {
      idCliente: usuario.id,
      descripcion: this.descripcion(),
      sector: this.sector(),
      sitioWeb: this.sitio_web(),
      perfil: null as any,
    };

    this.clienteServicio.registrarDatosInicialesCliente(cliente).subscribe({
      next: (respuesta) => {
        this.modalService.abrirExito(respuesta.valor);
        this.router.navigate(['/cliente-principal']);
      },
      error: (error) => {
        console.log('Error al registrar datos iniciales:', error);
        const mensaje = error.error?.valor || 'Error al registrar datos';
        this.modalService.abrirError(mensaje);
      },
    });
  }
}
