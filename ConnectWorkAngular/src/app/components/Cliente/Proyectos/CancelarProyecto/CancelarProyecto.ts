import { Component, inject, signal } from '@angular/core';
import { ProyectoServicio } from '../../../../services/ProyectoServicio';
import { ModalService } from '../../../../services/ModalService';
import { Proyecto } from '../../../../models/proyecto';
import { EstadoProyecto } from '../../../../enums/estado-proyecto';
import { LoginServicio } from '../../../../services/LoginServicio';
import { EstadoProyectoRequest } from '../../../../models/estado-proyecto-request';

@Component({
  selector: 'app-cancelar-proyecto',
  imports: [],
  templateUrl: './CancelarProyecto.html',
})
export default class CancelarProyecto {
  private proyectoServicio = inject(ProyectoServicio);
  private loginServicio = inject(LoginServicio);
  private modalService = inject(ModalService);
  protected proyectosActivos = signal<Proyecto[]>([]);
  protected proyectoSeleccionado = signal<number>(0);
  protected estado = signal<EstadoProyecto>(EstadoProyecto.CANCELADO);
  private idCliente = signal<number>(0);

  ngOnInit() {
    this.cargarProyectosActivos();
    this.estado.set(EstadoProyecto.CANCELADO);
  }

  private cargarProyectosActivos() {
    const usuario = this.loginServicio.getUsuario();
    if (!usuario) {
      this.modalService.abrirError('Usuario no autenticado');
      return;
    }
    this.idCliente.set(usuario.id);

    this.proyectoServicio.obtenerProyectosActivosPorCliente(this.idCliente()).subscribe({
      next: (proyectos) => {
        this.proyectosActivos.set(proyectos);
      },
      error: (error) => {
        console.error('Error al cargar los proyectos activos:', error);
        this.modalService.abrirError('Error al cargar los proyectos activos');
      },
    });
  }

  actualizarEstado() {
    if (!this.proyectoSeleccionado() || this.proyectoSeleccionado() === 0) {
      this.modalService.abrirAdvertencia('Por favor seleccione un proyecto');
      return;
    }

    const request: EstadoProyectoRequest = {
      idProyecto: this.proyectoSeleccionado(),
      nuevoEstado: this.estado(),
    };

    this.proyectoServicio.actualizarEstadoProyecto(request).subscribe({
      next: (respuesta) => {
        this.modalService.abrirExito(respuesta.valor);
        this.cargarProyectosActivos();
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
