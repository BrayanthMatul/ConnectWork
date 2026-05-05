import { Component, inject, signal } from '@angular/core';
import { HistorialPorcentajeServicio } from '../../../../services/HistorialPorcentajeServicio';
import { ModalService } from '../../../../services/ModalService';
import { HistorialPorcentajeComision } from '../../../../models/historial-porcentaje-comision';

@Component({
  selector: 'app-cambio-porcentaje',
  imports: [],
  templateUrl: './CambioPorcentaje.html',
})
export default class CambioPorcentaje {
  protected nuevoPorcentaje = signal<number>(0);
  protected porcentajeActual = signal<number>(0);
  private historialPorcentajeServicio = inject(HistorialPorcentajeServicio);
  private modalServicio = inject(ModalService);

  ngOnInit() {
    this.cargarPorcentajeActual();
  }

  private cargarPorcentajeActual() {
    this.historialPorcentajeServicio.obtenerUltimoHistorial().subscribe({
      next: (historial) => {
        if (historial) {
          this.porcentajeActual.set(historial.porcentajeComision);
        }
      },
      error: (error) => {
        console.error('Error al cargar el porcentaje actual:', error);
      },
    });
  }

  protected cambiarPorcentaje(): void {
    if (this.nuevoPorcentaje() < 0 || this.nuevoPorcentaje() > 100) {
      this.modalServicio.abrirError('El porcentaje debe estar entre 0 y 100.');
      return;
    }

    const nuevoHistorial: HistorialPorcentajeComision = {
      id: 0, // El ID se asignará automáticamente en el backend
      fechaHoraInicio: new Date(), // Se asignará automáticamente en el backend
      fechaHoraFin: new Date(), // Se asignará automáticamente en el backend cuando se registre un nuevo historial
      porcentajeComision: this.nuevoPorcentaje(),
    };

    this.historialPorcentajeServicio.registrarNuevoHistorial(nuevoHistorial).subscribe({
      next: (respuesta) => {
        this.modalServicio.abrirExito(respuesta.valor);
        this.cargarPorcentajeActual();
        this.nuevoPorcentaje.set(0);
      },
      error: (error) => {
        console.error('Error al cambiar el porcentaje:', error);
        this.modalServicio.abrirError(
          'Hubo un error al cambiar el porcentaje. Por favor, inténtalo de nuevo.',
        );
      },
    });
  }
}
