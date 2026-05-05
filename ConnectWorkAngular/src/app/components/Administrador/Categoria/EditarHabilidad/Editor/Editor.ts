import { Component, inject, input, output, signal } from '@angular/core';
import { ModalService } from '../../../../../services/ModalService';
import { Habilidad } from '../../../../../models/habilidad';
import { HabilidadServicio } from '../../../../../services/HabilidadServicio';

@Component({
  selector: 'app-editor',
  imports: [],
  templateUrl: './Editor.html',
})
export class Editor {
  private habilidadServicio = inject(HabilidadServicio);
  private servicioModal = inject(ModalService);
  public habilidad = input.required<Habilidad>();
  public volver = output<void>();
  protected nombre = signal<string>('');
  protected descripcion = signal<string>('');

  ngOnInit() {
    this.nombre.set(this.habilidad().nombre);
    this.descripcion.set(this.habilidad().descripcion);
  }

  protected guardarCambios() {
    const habilidadActualizada: Habilidad = {
      ...this.habilidad(),
      nombre: this.nombre(),
      descripcion: this.descripcion(),
    };

    this.habilidadServicio.actualizarHabilidad(habilidadActualizada).subscribe({
      next: (respuesta) => {
        this.servicioModal.abrirExito(respuesta.valor);
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
