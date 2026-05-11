import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HabilidadSolicitudServicio } from '../../../../services/HabilidadSolicitudServicio';
import { HabilidadServicio } from '../../../../services/HabilidadServicio';
import { CategoriaServicio } from '../../../../services/CategoriaServicio';
import { ModalService } from '../../../../services/ModalService';
import { HabilidadSolicitud } from '../../../../models/habilidad-solicitud';
import { Habilidad } from '../../../../models/habilidad';
import { Categoria } from '../../../../models/categoria';

interface HabilidadSolicitudConCategoria extends HabilidadSolicitud {
  nombreCategoria?: string;
}

@Component({
  selector: 'app-solicitud-habilidad',
  imports: [CommonModule],
  templateUrl: './SolicitudHabilidad.html',
})
export default class SolicitudHabilidad {
  private habilidadSolicitudServicio = inject(HabilidadSolicitudServicio);
  private habilidadServicio = inject(HabilidadServicio);
  private categoriaServicio = inject(CategoriaServicio);
  private modalServicio = inject(ModalService);

  protected solicitudes = signal<HabilidadSolicitudConCategoria[]>([]);
  private categorias = signal<Categoria[]>([]);

  ngOnInit() {
    this.cargarCategorias();
  }

  private cargarCategorias() {
    this.categoriaServicio.obtenerCategorias().subscribe({
      next: (categorias) => {
        this.categorias.set(categorias);
        this.cargarSolicitudes();
      },
      error: (error) => {
        this.modalServicio.abrirError('Error al cargar las categorías');
      },
    });
  }

  private cargarSolicitudes() {
    this.habilidadSolicitudServicio.obtenerHabilidadesNoRevisadas().subscribe({
      next: (solicitudes) => {
        const solicitudesConCategoria: HabilidadSolicitudConCategoria[] = solicitudes.map(
          (solicitud) => {
            const categoria = this.categorias().find((c) => c.id === solicitud.idCategoria);
            return {
              ...solicitud,
              nombreCategoria: categoria?.nombre || 'Categoría desconocida',
            };
          },
        );
        this.solicitudes.set(solicitudesConCategoria);
      },
      error: (error) => {
        this.modalServicio.abrirError('Error al cargar las solicitudes de habilidades');
      },
    });
  }

  protected aceptarSolicitud(solicitud: HabilidadSolicitudConCategoria) {
    // Crear la habilidad con los datos de la solicitud
    const habilidad: Habilidad = {
      id: 0,
      idCategoria: solicitud.idCategoria,
      nombre: solicitud.nombre,
      descripcion: solicitud.descripcion,
      activo: true,
    };

    // Registrar la habilidad
    this.habilidadServicio.registrarHabilidad(habilidad).subscribe({
      next: (respuesta) => {
        // Actualizar estado de la solicitud como aceptada
        this.habilidadSolicitudServicio.actualizarEstado(solicitud.id, true).subscribe({
          next: (respuesta) => {
            this.modalServicio.abrirExito('Habilidad aprobada y registrada correctamente');
            this.cargarSolicitudes();
          },
          error: (error) => {
            this.modalServicio.abrirError('Error al actualizar el estado de la solicitud');
          },
        });
      },
      error: (error) => {
        this.modalServicio.abrirError('Error al registrar la habilidad');
      },
    });
  }

  protected rechazarSolicitud(solicitud: HabilidadSolicitudConCategoria) {
    this.habilidadSolicitudServicio.actualizarEstado(solicitud.id, false).subscribe({
      next: (respuesta) => {
        this.modalServicio.abrirExito('Solicitud de habilidad rechazada');
        this.cargarSolicitudes();
      },
      error: (error) => {
        this.modalServicio.abrirError('Error al rechazar la solicitud');
      },
    });
  }
}
