import { Component, inject, signal } from '@angular/core';
import { ModalService } from '../../../../services/ModalService';
import { HabilidadServicio } from '../../../../services/HabilidadServicio';
import { Habilidad } from '../../../../models/habilidad';

@Component({
  selector: 'app-activar-desactivar-habilidad',
  imports: [],
  templateUrl: './ActivarDesactivarHabilidad.html',
})
export default class ActivarDesactivarHabilidad {
  private habilidadServicio = inject(HabilidadServicio);
  private modalService = inject(ModalService);
  protected habilidades = signal<Habilidad[]>([]);
  protected habilidadSeleccionada = signal<number>(0);
  protected estadoSeleccionado = signal<boolean>(true);

  ngOnInit() {
    this.cargarDeHabilidades();
    this.estadoSeleccionado.set(true);
  }

  private cargarDeHabilidades() {
    this.habilidadServicio.obtenerHabilidades().subscribe({
      next: (habilidades) => {
        this.habilidades.set(habilidades);
      },
      error: (error) => {
        console.error('Error al cargar las habilidades:', error);
        this.modalService.abrirError('Error al cargar las habilidades');
      },
    });
  }

  actualizarEstado() {
    if (!this.habilidadSeleccionada() || this.habilidadSeleccionada() === 0) {
      this.modalService.abrirAdvertencia('Por favor seleccione una habilidad');
      return;
    }

    console.log('Nuevo estado:', this.estadoSeleccionado());
    this.habilidadServicio
      .actualizarEstadoHabilidad(this.habilidadSeleccionada(), this.estadoSeleccionado())
      .subscribe({
        next: (respuesta) => {
          this.modalService.abrirExito(respuesta.valor);
          this.cargarDeHabilidades();
        },
        error: (error) => {
          const mensaje = error.error.valor;
          this.modalService.abrirError(mensaje);
        },
      });
  }

  toNumber(value: string): number {
    return Number(value);
  }
}
