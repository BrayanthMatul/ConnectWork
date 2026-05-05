import { Component, signal } from '@angular/core';
import { Categoria } from '../../../../models/categoria';
import { SeleccionarCategoria } from './SeleccionarCategoria/SeleccionarCategoria';
import { Editor } from './Editor/Editor';

@Component({
  selector: 'app-editar-categoria',
  imports: [SeleccionarCategoria, Editor],
  templateUrl: './EditarCategoria.html',
})
export default class EditarCategoria {
  protected categoriaSeleccionada = signal<Categoria | null>(null);

  protected manejarCategoriaSeleccionada(categoria: Categoria) {
    this.categoriaSeleccionada.set(categoria);
  }

  protected cancelarEdicion() {
    this.categoriaSeleccionada.set(null);
  }
}
