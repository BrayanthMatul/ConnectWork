import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriaSolicitudServicio } from '../../../../services/CategoriaSolicitudServicio';
import { CategoriaServicio } from '../../../../services/CategoriaServicio';
import { ModalService } from '../../../../services/ModalService';
import { CategoriaSolicitud } from '../../../../models/categoria-solicitud';
import { Categoria } from '../../../../models/categoria';

@Component({
  selector: 'app-solicitud-categoria',
  imports: [CommonModule],
  templateUrl: './SolicitudCategoria.html',
})
export default class SolicitudCategoria {
  private categoriaSolicitudServicio = inject(CategoriaSolicitudServicio);
  private categoriaServicio = inject(CategoriaServicio);
  private modalServicio = inject(ModalService);

  protected solicitudes = signal<CategoriaSolicitud[]>([]);

  ngOnInit() {
    this.cargarSolicitudes();
  }

  private cargarSolicitudes() {
    this.categoriaSolicitudServicio.obtenerCategoriasNoRevisadas().subscribe({
      next: (solicitudes) => {
        this.solicitudes.set(solicitudes);
      },
      error: (error) => {
        this.modalServicio.abrirError('Error al cargar las solicitudes de categorías');
      },
    });
  }

  protected aceptarSolicitud(solicitud: CategoriaSolicitud) {
    // Crear la categoría con los datos de la solicitud
    const categoria: Categoria = {
      id: 0,
      nombre: solicitud.nombre,
      descripcion: solicitud.descripcion,
      activo: true,
    };

    // Registrar la categoría
    this.categoriaServicio.registrarCategoria(categoria).subscribe({
      next: (respuesta) => {
        // Actualizar estado de la solicitud como aceptada
        this.categoriaSolicitudServicio.actualizarEstado(solicitud.id, true).subscribe({
          next: (respuesta) => {
            this.modalServicio.abrirExito('Categoría aprobada y registrada correctamente');
            this.cargarSolicitudes();
          },
          error: (error) => {
            this.modalServicio.abrirError('Error al actualizar el estado de la solicitud');
          },
        });
      },
      error: (error) => {
        this.modalServicio.abrirError('Error al registrar la categoría');
      },
    });
  }

  protected rechazarSolicitud(solicitud: CategoriaSolicitud) {
    this.categoriaSolicitudServicio.actualizarEstado(solicitud.id, false).subscribe({
      next: (respuesta) => {
        this.modalServicio.abrirExito('Solicitud de categoría rechazada');
        this.cargarSolicitudes();
      },
      error: (error) => {
        this.modalServicio.abrirError('Error al rechazar la solicitud');
      },
    });
  }
}
