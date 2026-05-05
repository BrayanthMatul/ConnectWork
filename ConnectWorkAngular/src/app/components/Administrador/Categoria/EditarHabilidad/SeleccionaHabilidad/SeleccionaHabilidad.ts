import { Component, inject, output, signal } from '@angular/core';
import { HabilidadServicio } from '../../../../../services/HabilidadServicio';
import { ModalService } from '../../../../../services/ModalService';
import { Habilidad } from '../../../../../models/habilidad';

@Component({
  selector: 'app-selecciona-habilidad',
  imports: [],
  templateUrl: './SeleccionaHabilidad.html',
})
export class SeleccionaHabilidad {
  private habilidadServicio = inject(HabilidadServicio);
  private servicioModal = inject(ModalService);
  protected habilidades = signal<Habilidad[]>([]);
  protected habilidadSeleccionada = signal<Habilidad | null>(null);
  protected habilidadEditar = output<Habilidad>();

  ngOnInit() {
    this.cargarHabilidades();
  }

  private cargarHabilidades() {
    this.habilidadServicio.obtenerHabilidades().subscribe((habilidades) => {
      this.habilidades.set(habilidades.filter((h) => h.activo));
    });
  }

  protected seleccionar(nombre: string) {
    const encontrado = this.habilidades().find((h) => h.nombre === nombre);
    if (encontrado) {
      this.habilidadSeleccionada.set(encontrado);
    }
  }

  protected emitirHabilidadSeleccionada() {
    if (this.habilidadSeleccionada()) {
      this.habilidadEditar.emit(this.habilidadSeleccionada()!);
    } else {
      this.servicioModal.abrirAdvertencia(
        'Por favor, selecciona una habilidad antes de continuar.',
      );
    }
  }
}
