import { Component, inject, input, output, signal } from '@angular/core';
import { Propuesta } from '../../../../models/propuesta';
import { ProyectoServicio } from '../../../../services/ProyectoServicio';
import { PropuestaServicio } from '../../../../services/PropuestaServicio';
import { CardPropuesta } from './CardPropuesta/CardPropuesta';
import { EstadoPropuesta } from '../../../../enums/estado-propuesta';
import { forkJoin } from 'rxjs';

export interface PropuestaAceptada {
  idPropuesta: number;
  idProyecto: number;
}

@Component({
  selector: 'app-propuestas-por-proyecto',
  imports: [CardPropuesta],
  templateUrl: './PropuestasPorProyecto.html',
})
export class PropuestasPorProyecto {
  private proyectoServicio = inject(ProyectoServicio);
  private propuestaServicio = inject(PropuestaServicio);
  public propuestas = input.required<Propuesta[]>();
  public idProyecto = input.required<number>();
  public idAceptacion = output<PropuestaAceptada>();
  public propuestasAgotadas = output<number>();
  protected nombreProyecto = signal<string>('');
  protected propuestasLocales = signal<Propuesta[]>([]);

  ngOnInit() {
    this.cargarNombreProyecto();
    this.propuestasLocales.set([...this.propuestas()]);
  }

  private cargarNombreProyecto(): void {
    this.proyectoServicio.obtenerProyectoPorId(this.idProyecto()).subscribe({
      next: (proyecto) => {
        if (proyecto) {
          this.nombreProyecto.set(proyecto.titulo);
        }
      },
      error: (error) => {
        console.error('Error al cargar el nombre del proyecto', error);
      },
    });
  }

  protected eliminarPropuesta(idPropuesta: number): void {
    this.propuestaServicio
      .actualizarEstadoPropuesta({
        idPropuesta,
        nuevoEstado: EstadoPropuesta.RECHAZADA,
      })
      .subscribe({
        next: () => {
          const propuestasActuales = this.propuestasLocales();
          const propuestasFiltradasn = propuestasActuales.filter((p) => p.id !== idPropuesta);
          this.propuestasLocales.set(propuestasFiltradasn);

          if (propuestasFiltradasn.length === 0) {
            this.propuestasAgotadas.emit(this.idProyecto());
          }
        },
        error: (error) => {
          console.error('Error al rechazar la propuesta', error);
        },
      });
  }

  protected aceptarPropuestaEmit(idPropuesta: number): void {
    this.marcarPropuestasComoRechazadasExcepto(idPropuesta);
    this.idAceptacion.emit({ idPropuesta, idProyecto: this.idProyecto() });
  }

  protected marcarPropuestasComoRechazadasExcepto(idPropuestaAceptada: number): void {
    const propuestasActuales = this.propuestasLocales();
    const propuestasArechazar = propuestasActuales.filter((p) => p.id !== idPropuestaAceptada);

    if (propuestasArechazar.length === 0) {
      return;
    }

    const actualizaciones = propuestasArechazar.map((propuesta) =>
      this.propuestaServicio.actualizarEstadoPropuesta({
        idPropuesta: propuesta.id,
        nuevoEstado: EstadoPropuesta.RECHAZADA,
      }),
    );

    forkJoin(actualizaciones).subscribe({
      next: () => {
        const propuestasActualizadas = propuestasActuales.filter(
          (p) => p.id === idPropuestaAceptada,
        );
        this.propuestasLocales.set(propuestasActualizadas);
      },
      error: (error) => {
        console.error('Error al rechazar las otras propuestas', error);
      },
    });
  }
}
