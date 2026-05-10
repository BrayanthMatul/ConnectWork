import { Component, inject, signal } from '@angular/core';
import { forkJoin } from 'rxjs';
import { Filtro, FiltroProyectos } from './Filtro/Filtro';
import { Lista } from './Lista/Lista';
import { Proyecto } from '../../../models/proyecto';
import { ProyectoServicio } from '../../../services/ProyectoServicio';
import { ProyectoHabilidadServicio } from '../../../services/ProyectoHabilidadServicio';
import { ProyectoHabilidad } from '../../../models/proyecto-habilidad';
import { Habilidad } from '../../../models/habilidad';

@Component({
  selector: 'app-explorar-proyectos',
  imports: [Filtro, Lista],
  templateUrl: './ExplorarProyectos.html',
  host: {
    class: 'flex-1 flex flex-col w-full self-stretch',
  },
})
export default class ExplorarProyectos {
  protected filtroActivo = signal<FiltroProyectos | null>(null);
  protected proyectosFiltrados = signal<Proyecto[]>([]);

  private servicioProyectos = inject(ProyectoServicio);
  private servicioProyectoHabilidades = inject(ProyectoHabilidadServicio);

  private todosLosProyectos: Proyecto[] = [];
  private todasLasHabilidades: ProyectoHabilidad[] = [];

  ngOnInit() {
    this.cargarProyectos();
  }

  protected aplicarFiltro(filtro: FiltroProyectos) {
    this.filtroActivo.set(filtro);

    let proyectos = [...this.todosLosProyectos];

    if (filtro.rangoActivo) {
      proyectos = proyectos.filter(
        (p) =>
          p.presupuestoMaximo >= (filtro.rangoMinimo ?? 0) &&
          p.presupuestoMaximo <= (filtro.rangoMaximo ?? Infinity),
      );
    }

    if (filtro.categoriaActiva) {
      proyectos = proyectos.filter((p) => p.idCategoria === filtro.idCategoria);
    }

    if (filtro.habilidadesSeleccionadas && filtro.habilidadesSeleccionadas.length > 0) {
      proyectos = this.filtrarPorHabilidades(proyectos, filtro.habilidadesSeleccionadas);
    }

    this.proyectosFiltrados.set(proyectos);
  }

  private cargarProyectos() {
    // Cargamos proyectos Y habilidades en paralelo una sola vez
    forkJoin({
      proyectos: this.servicioProyectos.obtenerProyectosAbiertos(),
      habilidades: this.servicioProyectoHabilidades.obtenerProyectoHabilidades(),
    }).subscribe(({ proyectos, habilidades }) => {
      this.todasLasHabilidades = habilidades;

      // Enriquecemos cada proyecto con sus habilidades
      this.todosLosProyectos = proyectos.map((proyecto) => ({
        ...proyecto,
        habilidades: habilidades.filter((h) => h.idProyecto === proyecto.id),
      }));

      this.proyectosFiltrados.set(this.todosLosProyectos);
    });
  }

  private filtrarPorHabilidades(
    proyectos: Proyecto[],
    habilidadesSeleccionadas: Habilidad[],
  ): Proyecto[] {
    return proyectos.filter((proyecto) =>
      habilidadesSeleccionadas.every((habilidad) =>
        proyecto.habilidades?.some((h) => h.idHabilidad === habilidad.id),
      ),
    );
  }
}
