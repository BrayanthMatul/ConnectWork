import { Component, inject, input, output, signal } from '@angular/core';
import { Categoria } from '../../../../../models/categoria';
import { CategoriaServicio } from '../../../../../services/CategoriaServicio';
import { ModalService } from '../../../../../services/ModalService';

@Component({
  selector: 'app-editor',
  imports: [],
  templateUrl: './Editor.html',
})
export class Editor {
  private categoriaServicio = inject(CategoriaServicio);
  private servicioModal = inject(ModalService);
  public categoria = input.required<Categoria>();
  public volver = output<void>();
  protected nombre = signal<string>('');
  protected descripcion = signal<string>('');

  ngOnInit() {
    this.nombre.set(this.categoria().nombre);
    this.descripcion.set(this.categoria().descripcion);
  }

  protected guardarCambios() {
    const categoriaActualizada: Categoria = {
      ...this.categoria(),
      nombre: this.nombre(),
      descripcion: this.descripcion(),
    };

    this.categoriaServicio.actualizarCategoria(categoriaActualizada).subscribe({
      next: () => {
        this.servicioModal.abrirExito('Categoría actualizada exitosamente.');
      },
      error: (error) => {
        this.servicioModal.abrirError(error.error.valor);
      },
    });
  }

  protected regresar() {
    this.volver.emit();
  }
}
