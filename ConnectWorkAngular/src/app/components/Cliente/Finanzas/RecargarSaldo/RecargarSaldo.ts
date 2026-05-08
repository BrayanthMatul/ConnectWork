import { Component, inject, signal } from '@angular/core';
import { LoginServicio } from '../../../../services/LoginServicio';
import { ModalService } from '../../../../services/ModalService';
import { ClienteServicio } from '../../../../services/ClienteServicio';
import { SaldoRequest } from '../../../../models/saldo-request';

@Component({
  selector: 'app-recargar-saldo',
  imports: [],
  templateUrl: './RecargarSaldo.html',
})
export default class RecargarSaldo {
  private clienteServicio = inject(ClienteServicio);
  private servicioLogin = inject(LoginServicio);
  private servicioModal = inject(ModalService);
  protected monto = signal<number>(0);

  protected recargarSaldo(): void {
    const usuario = this.servicioLogin.getUsuario();
    if (!usuario) {
      this.servicioModal.abrirError(
        'No se encontró el usuario. Por favor, inicie sesión nuevamente.',
      );
      return;
    }

    const saldoRequest: SaldoRequest = {
      id: usuario.id,
      monto: this.monto(),
    };

    this.clienteServicio.agregarSaldoCliente(saldoRequest).subscribe({
      next: (respuesta) => {
        if (respuesta) {
          this.servicioModal.abrirExito('Saldo recargado exitosamente.');
          this.monto.set(0);
        } else {
          this.servicioModal.abrirError(
            'No se pudo recargar el saldo. Por favor, inténtelo nuevamente.',
          );
        }
      },
      error: (error) => {
        console.error('Error al recargar saldo:', error);
        this.servicioModal.abrirError(
          'Ocurrió un error al recargar el saldo. Por favor, inténtelo nuevamente.',
        );
      },
    });
  }
}
