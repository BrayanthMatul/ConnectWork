import { inject, Injectable, signal, computed } from '@angular/core';
import { Habilidad } from '../models/habilidad';
import { CategoriaServicio } from './CategoriaServicio';
import { HabilidadServicio } from './HabilidadServicio';
import { Categoria } from '../models/categoria';

export interface CategoriaConHabilidades {
  id: number;
  nombre: string;
  habilidades: Habilidad[];
}

@Injectable({
  providedIn: 'root',
})
export class CategoriaListaServicio {
  private categoriaServicio = inject(CategoriaServicio);
  private habilidadServicio = inject(HabilidadServicio);
  private categorias = signal<Categoria[]>([]);
  private habilidades = signal<Habilidad[]>([]);

  public categoriasConHabilidades = computed(() => {
    return this.categorias().map((categoria) => {
      const habilidadesCategoria = this.habilidades().filter(
        (habilidad) => habilidad.idCategoria === categoria.id,
      );
      return {
        id: categoria.id,
        nombre: categoria.nombre,
        habilidades: habilidadesCategoria,
      };
    });
  });

  public cargarDatos() {
    this.cargarCategorias();
    this.cargarHabilidades();
  }

  private cargarCategorias() {
    this.categoriaServicio.obtenerCategorias().subscribe({
      next: (categorias) => {
        this.categorias.set(categorias);
      },
      error: (error) => {
        console.error('Error al cargar las categorias:', error);
      },
    });
  }

  private cargarHabilidades() {
    this.habilidadServicio.obtenerHabilidades().subscribe({
      next: (habilidades) => {
        this.habilidades.set(habilidades);
      },
      error: (error) => {
        console.error('Error al cargar las habilidades:', error);
      },
    });
  }

  public categoriaPorIdHabilidad(idHabilidad: number): Categoria | undefined {
    const habilidad = this.habilidades().find((h) => h.id === idHabilidad);
    if (habilidad) {
      return this.categorias().find((c) => c.id === habilidad.idCategoria);
    }
    return undefined;
  }
}
