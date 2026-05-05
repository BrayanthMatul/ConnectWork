import { Component, signal } from '@angular/core';
import { Habilidad } from '../../../../models/habilidad';
import { SeleccionaHabilidad } from './SeleccionaHabilidad/SeleccionaHabilidad';
import { Editor } from './Editor/Editor';

@Component({
  selector: 'app-editar-habilidad',
  imports: [SeleccionaHabilidad, Editor],
  templateUrl: './EditarHabilidad.html',
})
export default class EditarHabilidad {
  protected habilidadSeleccionada = signal<Habilidad | null>(null);

  protected manejarHabilidadSeleccionada(habilidad: Habilidad) {
    this.habilidadSeleccionada.set(habilidad);
  }

  protected cancelarEdicion() {
    this.habilidadSeleccionada.set(null);
  }
}
