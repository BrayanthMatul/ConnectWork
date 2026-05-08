import { Component, computed, inject, signal } from '@angular/core';
import { Habilidad } from '../../../../models/habilidad';
import { ModalService } from '../../../../services/ModalService';
import { LoginServicio } from '../../../../services/LoginServicio';
import { CategoriaListaServicio } from '../../../../services/CategoriaListaServicio';

@Component({
  selector: 'app-filtro',
  imports: [],
  templateUrl: './Filtro.html',
})
export class Filtro {
  private categoriaListaServicio = inject(CategoriaListaServicio);
  private loginServicio = inject(LoginServicio);
  private modalService = inject(ModalService);

  protected categoriaSeleccionada = signal<number>(0);
  protected habilidadSeleccionada = signal<number>(0);
  protected habilidadesSeleccionadas = signal<Habilidad[]>([]);
  protected habilidadesDeCategoria = signal<Habilidad[]>([]);
  protected idCategoria = signal<number>(0);

  protected categoriasConHabilidades = computed(() =>
    this.categoriaListaServicio.categoriasConHabilidades(),
  );

  protected actualizarCategoria() {
    this.idCategoria.set(this.categoriaSeleccionada());
    this.actualiazarHabilidades();
  }

  private actualiazarHabilidades() {
    this.habilidadesSeleccionadas.set([]);
    const categoria = this.categoriasConHabilidades().find(
      (c) => c.id === this.categoriaSeleccionada(),
    );
    if (categoria) {
      this.habilidadesDeCategoria.set(categoria.habilidades);
    }
  }

  protected agregarHabilidad() {
    const idHabilidad = this.habilidadSeleccionada();
    if (idHabilidad) {
      const habilidad = this.habilidadesDeCategoria().find((h) => h.id === idHabilidad);
      if (habilidad) {
        this.habilidadesSeleccionadas.update((habilidades) => [...habilidades, habilidad]);
        this.habilidadesDeCategoria.update((habilidades) =>
          habilidades.filter((h) => h.id !== idHabilidad),
        );
        this.habilidadSeleccionada.set(0);
      }
    }
  }

  protected removerHabilidad(id: number) {
    const habilidad = this.habilidadesSeleccionadas().find((h) => h.id === id);
    if (habilidad) {
      this.habilidadesSeleccionadas.update((habilidades) => habilidades.filter((h) => h.id !== id));
      this.habilidadesDeCategoria.update((habilidades) => [...habilidades, habilidad]);
      this.habilidadSeleccionada.set(0);
    }
  }

  toNumber(value: string): number {
    return Number(value);
  }
}
