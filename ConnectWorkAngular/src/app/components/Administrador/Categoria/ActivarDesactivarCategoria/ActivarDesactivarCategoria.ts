import { Component, inject, signal } from '@angular/core';
import { ModalService } from '../../../../services/ModalService';
import { CategoriaServicio } from '../../../../services/CategoriaServicio';
import { Categoria } from '../../../../models/categoria';

@Component({
  selector: 'app-activar-desactivar-categoria',
  imports: [],
  templateUrl: './ActivarDesactivarCategoria.html',
})
export default class ActivarDesactivarCategoria {
  private categoriaServicio = inject(CategoriaServicio);
  private modalService = inject(ModalService);
  protected categorias = signal<Categoria[]>([]);
  protected categoriaSeleccionada = signal<number>(0);
  protected estadoSeleccionado = signal<boolean>(true);

  ngOnInit() {
    this.cargarDeCategorias();
    this.estadoSeleccionado.set(true);
  }

  private cargarDeCategorias() {
    this.categoriaServicio.obtenerCategorias().subscribe({
      next: (categorias) => {
        this.categorias.set(categorias);
      },
      error: (error) => {
        console.error('Error al cargar las categorias:', error);
        this.modalService.abrirError('Error al cargar las categorias');
      },
    });
  }

  actualizarEstado() {
    if (!this.categoriaSeleccionada() || this.categoriaSeleccionada() === 0) {
      this.modalService.abrirAdvertencia('Por favor seleccione una categoria');
      return;
    }

    this.categoriaServicio
      .actualizarEstadoCategoria(this.categoriaSeleccionada(), this.estadoSeleccionado())
      .subscribe({
        next: (respuesta) => {
          console.log('Estado actualizado:', this.estadoSeleccionado());
          this.modalService.abrirExito(respuesta.valor);
          this.cargarDeCategorias();
        },
        error: (error) => {
          const mensaje = error.error.valor;
          this.modalService.abrirError(mensaje);
        },
      });
  }

  toNumber(value: string): number {
    return Number(value);
  }
}
