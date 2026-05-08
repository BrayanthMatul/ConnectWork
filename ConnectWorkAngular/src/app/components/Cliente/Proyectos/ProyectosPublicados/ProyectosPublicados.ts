import { Component, inject, signal } from '@angular/core';
import { ProyectoServicio } from '../../../../services/ProyectoServicio';
import { Proyecto } from '../../../../models/proyecto';
import { LoginServicio } from '../../../../services/LoginServicio';
import { ModalService } from '../../../../services/ModalService';

@Component({
  selector: 'app-proyectos-publicados',
  imports: [],
  templateUrl: './ProyectosPublicados.html',
})
export default class ProyectosPublicados {
  private proyectoServicio = inject(ProyectoServicio);
  private loginServicio = inject(LoginServicio);
  private modalService = inject(ModalService);
  protected proyectosActivos = signal<Proyecto[]>([]);
  private idCliente = signal<number>(0);

  ngOnInit() {
    this.cargarProyectos();
  }

  private cargarProyectos() {
    const usuario = this.loginServicio.getUsuario();
    if (!usuario) {
      return;
    }
    this.idCliente.set(usuario.id);

    this.proyectoServicio.obtenerProyectosActivosPorCliente(this.idCliente()).subscribe({
      next: (proyectos) => {
        this.proyectosActivos.set(proyectos);
      },
      error: (error) => {
        this.modalService.abrirError('Error al cargar los proyectos activos');
      },
    });
  }
}
