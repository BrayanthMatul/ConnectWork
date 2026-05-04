import { Component, inject, signal } from '@angular/core';
import { PerfilServicio } from '../../../../services/PerfilServicio';
import { Perfil } from '../../../../models/perfil';
import { ModalService } from '../../../../services/ModalService';

@Component({
  selector: 'app-activar-desactivar-usuarios',
  imports: [],
  templateUrl: './ActivarDesactivarUsuarios.html',
})
export default class ActivarDesactivarUsuarios {
  private perfilServicio = inject(PerfilServicio);
  private modalService = inject(ModalService);
  protected perfiles = signal<Perfil[]>([]);
  protected perfilSeleccionado = signal<number>(0);
  protected estadoSeleccionado = signal<boolean>(false);

  ngOnInit() {
    this.cargarDePerfiles();
  }

  private cargarDePerfiles() {
    this.perfilServicio.obtenerPerfiles().subscribe({
      next: (perfiles) => {
        this.perfiles.set(perfiles);
      },
      error: (error) => {
        console.error('Error al cargar los perfiles:', error);
        this.modalService.abrirError('Error al cargar los perfiles');
      },
    });
  }

  actualizarEstado() {
    if (!this.perfilSeleccionado() || this.perfilSeleccionado() === 0) {
      this.modalService.abrirError('Por favor seleccione un perfil');
      return;
    }

    this.perfilServicio
      .actualizarEstadoPerfil(this.perfilSeleccionado(), this.estadoSeleccionado())
      .subscribe({
        next: (respuesta) => {
          this.modalService.abrirExito(respuesta.valor);
          this.cargarDePerfiles();
        },
        error: (error) => {
          console.error('Error al actualizar el estado del perfil:', error);
          const mensaje = error.error.error || 'Error al actualizar estado';
          this.modalService.abrirError(mensaje);
        },
      });
  }

  toNumber(value: string): number {
    return Number(value);
  }
}
