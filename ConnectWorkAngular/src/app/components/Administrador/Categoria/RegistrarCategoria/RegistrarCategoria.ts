import { Component, inject, signal } from '@angular/core';
import { CategoriaServicio } from '../../../../services/CategoriaServicio';
import { ModalService } from '../../../../services/ModalService';
import { Categoria } from '../../../../models/categoria';

@Component({
  selector: 'app-registrar-categoria',
  imports: [],
  templateUrl: './RegistrarCategoria.html',
})
export default class RegistrarCategoria {
  protected nombre = signal<string>('');
  protected descripcion = signal<string>('');
  private categoriaServicio = inject(CategoriaServicio);
  private modalServicio = inject(ModalService);

  protected registrarCategoria() {
    const categoria: Categoria = {
      id: 0,
      nombre: this.nombre(),
      descripcion: this.descripcion(),
      activo: true,
    };

    this.categoriaServicio.registrarCategoria(categoria).subscribe({
      next: (respuesta) => {
        this.modalServicio.abrirExito(respuesta.valor);
        this.limpiarFormulario();
      },
      error: (error) => {
        this.modalServicio.abrirError(error.error.valor);
      },
    });
  }

  private limpiarFormulario() {
    this.nombre.set('');
    this.descripcion.set('');
  }
}
