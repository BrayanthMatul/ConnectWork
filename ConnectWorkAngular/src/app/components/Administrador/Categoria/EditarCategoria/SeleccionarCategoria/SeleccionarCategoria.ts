import { Component, inject, output, signal } from '@angular/core';
import { CategoriaServicio } from '../../../../../services/CategoriaServicio';
import { ModalService } from '../../../../../services/ModalService';
import { Categoria } from '../../../../../models/categoria';

@Component({
  selector: 'app-seleccionar-categoria',
  imports: [],
  templateUrl: './SeleccionarCategoria.html',
})
export class SeleccionarCategoria {
  private categoriaServicio = inject(CategoriaServicio);
  private servicioModal = inject(ModalService);
  protected categorias = signal<Categoria[]>([]);
  protected categoriaSeleccionado = signal<Categoria | null>(null);
  protected categoriaEditar = output<Categoria>();

  ngOnInit() {
    this.cargarCategorias();
  }

  private cargarCategorias() {
    this.categoriaServicio.obtenerCategorias().subscribe((categorias) => {
      this.categorias.set(categorias.filter((c) => c.activo));
    });
  }

  protected seleccionar(nombre: string) {
    const encontrado = this.categorias().find((c) => c.nombre === nombre);
    if (encontrado) {
      this.categoriaSeleccionado.set(encontrado);
    }
  }

  protected emitirCategoriaSeleccionada() {
    if (this.categoriaSeleccionado()) {
      this.categoriaEditar.emit(this.categoriaSeleccionado()!);
    } else {
      this.servicioModal.abrirAdvertencia(
        'Por favor, selecciona una categoría antes de continuar.',
      );
    }
  }
}
