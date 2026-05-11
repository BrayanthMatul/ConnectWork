import { Component, inject, input, signal } from '@angular/core';
import { PropuestaServicio } from '../../../../services/PropuestaServicio';
import { ProyectoServicio } from '../../../../services/ProyectoServicio';
import { Contrato } from '../../../../models/contrato';

@Component({
  selector: 'app-card-contrato',
  imports: [],
  templateUrl: './CardContrato.html',
})
export class CardContrato {
  private propuestaServicio = inject(PropuestaServicio);
  private proyectoServicio = inject(ProyectoServicio);

  public contrato = input.required<Contrato>();
  protected nombreProyecto = signal<string>('');
  protected plazoEntrega = signal<number>(0);

  ngOnInit() {
    this.cargarDetalles();
  }

  private cargarDetalles(): void {
    // Obtener la propuesta para conseguir el idProyecto
    this.propuestaServicio.obtenerPropuestaPorId(this.contrato().idPropuesta).subscribe({
      next: (propuesta) => {
        if (propuesta) {
          this.plazoEntrega.set(propuesta.plazoEntrega);

          // Obtener el proyecto para conseguir el nombre
          this.proyectoServicio.obtenerProyectoPorId(propuesta.idProyecto).subscribe({
            next: (proyecto) => {
              if (proyecto) {
                this.nombreProyecto.set(proyecto.titulo);
              }
            },
            error: (error) => {
              console.error('Error al cargar el proyecto', error);
            },
          });
        }
      },
      error: (error) => {
        console.error('Error al cargar la propuesta', error);
      },
    });
  }

  protected agregarEntrega(): void {
    // Lógica para agregar entrega - será implementada
  }
}
