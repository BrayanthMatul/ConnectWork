import { Component, inject, signal } from '@angular/core';
import { ModalService } from '../../../services/ModalService';
import { CategoriaSolicitudServicio } from '../../../services/CategoriaSolicitudServicio';
import { LoginServicio } from '../../../services/LoginServicio';
import { CategoriaSolicitud } from '../../../models/categoria-solicitud';

@Component({
  selector: 'app-solicitar-categoria',
  imports: [],
  templateUrl: './SolicitarCategoria.html',
})
export default class SolicitarCategoria {
  private modalServicio = inject(ModalService);
  private categoriaSolicitudServicio = inject(CategoriaSolicitudServicio);
  private loginServicio = inject(LoginServicio);
  private idCliente = signal<number>(0);
  protected nombre = signal<string>('');
  protected descripcion = signal<string>('');

  protected solicitarCategoria() {
    const usuario = this.loginServicio.getUsuario();
    if (!usuario) {
      this.modalServicio.abrirError(
        'No se pudo obtener la información del usuario. Por favor, inicia sesión nuevamente.',
      );
      return;
    }
    this.idCliente.set(usuario.id);

    const solicitud: CategoriaSolicitud = {
      id: 0, //Se asignará automáticamente en el backend
      nombre: this.nombre(),
      descripcion: this.descripcion(),
      aceptada: false,
      revisada: false,
      idCliente: this.idCliente(),
    };

    this.categoriaSolicitudServicio.registrarSolicitud(solicitud).subscribe({
      next: () => {
        this.modalServicio.abrirExito('Solicitud de categoría enviada exitosamente');
        this.nombre.set('');
        this.descripcion.set('');
      },
      error: (error) => {
        const mensajeError =
          error.error?.valor || 'Error al registrar la solicitud. Por favor, intenta nuevamente.';
        this.modalServicio.abrirError(mensajeError);
      },
    });
  }
}
