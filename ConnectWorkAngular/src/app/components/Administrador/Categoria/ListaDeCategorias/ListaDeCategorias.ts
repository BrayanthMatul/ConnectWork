import { Component, inject, signal } from '@angular/core';
import { Categoria } from '../../../../models/categoria';
import { CategoriaServicio } from '../../../../services/CategoriaServicio';

@Component({
  selector: 'app-lista-de-categorias',
  imports: [],
  templateUrl: './ListaDeCategorias.html',
})
export default class ListaDeCategorias {
  private categoriaServicio = inject(CategoriaServicio);
  protected categorias = signal<Categoria[]>([]);

  ngOnInit() {
    this.cargarCategorias();
  }

  private cargarCategorias() {
    this.categoriaServicio.obtenerCategorias().subscribe((categorias) => {
      this.categorias.set(categorias);
    });
  }
}
