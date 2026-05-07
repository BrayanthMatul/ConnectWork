import { Component, inject, computed } from '@angular/core';
import { CategoriaListaServicio } from '../../../../services/CategoriaListaServicio';

@Component({
  selector: 'app-lista-de-habilidades',
  imports: [],
  templateUrl: './ListaDeHabilidades.html',
})
export default class ListaDeHabilidades {
  private categoriaListaServicio = inject(CategoriaListaServicio);
  protected categoriasConHabilidades = computed(() =>
    this.categoriaListaServicio.categoriasConHabilidades(),
  );

  ngOnInit() {
    this.categoriaListaServicio.cargarDatos();
  }
}
