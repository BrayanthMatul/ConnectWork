import { Component, inject, signal } from '@angular/core';
import { ProyectoServicio } from '../../../services/ProyectoServicio';
import { PropuestaServicio } from '../../../services/PropuestaServicio';
import { ModalService } from '../../../services/ModalService';
import { PropuestasPorProyecto } from './PropuestasPorProyecto/PropuestasPorProyecto';
import { Proyecto } from '../../../models/proyecto';
import { Propuesta } from '../../../models/propuesta';
import { Contrato } from '../../../models/contrato';
import { EstadoContrato } from '../../../enums/estado-contrato';
import { LoginServicio } from '../../../services/LoginServicio';
import { ContratoServicio } from '../../../services/ContratoServicio';

interface ProyectoConPropuestas {
  proyecto: Proyecto;
  propuestas: Propuesta[];
}

@Component({
  selector: 'app-propuestas-recibidas',
  imports: [PropuestasPorProyecto],
  templateUrl: './PropuestasRecibidas.html',
})
export default class PropuestasRecibidas {
  private proyectoServicio = inject(ProyectoServicio);
  private propuestaServicio = inject(PropuestaServicio);
  private contratoServicio = inject(ContratoServicio);
  private modalService = inject(ModalService);
  private loginServicio = inject(LoginServicio);

  protected proyectosConPropuestas = signal<ProyectoConPropuestas[]>([]);

  ngOnInit() {
    this.cargarProyectosConPropuestas();
  }

  private cargarProyectosConPropuestas(): void {
    const usuarioId = this.loginServicio.getUsuario()!.id;

    this.proyectoServicio.obtenerProyectosActivosPorCliente(usuarioId).subscribe({
      next: (proyectos) => {
        this.propuestaServicio.obtenerPropuestasPendientes().subscribe({
          next: (propuestas) => {
            const proyectosConPropuestas: ProyectoConPropuestas[] = proyectos
              .map((proyecto) => ({
                proyecto,
                propuestas: propuestas.filter((p) => p.idProyecto === proyecto.id),
              }))
              .filter((pc) => pc.propuestas.length > 0);

            this.proyectosConPropuestas.set(proyectosConPropuestas);
          },
          error: (error) => {
            console.error('Error al cargar propuestas', error);
          },
        });
      },
      error: (error) => {
        console.error('Error al cargar proyectos', error);
      },
    });
  }

  protected aceptarPropuesta(propuestaAceptada: { idPropuesta: number; idProyecto: number }): void {
    // Obtener la propuesta aceptada
    const proyectoConPropuestas = this.proyectosConPropuestas().find(
      (pc) => pc.proyecto.id === propuestaAceptada.idProyecto,
    );

    if (!proyectoConPropuestas) {
      this.modalService.abrirError('No se encontró el proyecto');
      return;
    }

    const propuesta = proyectoConPropuestas.propuestas.find(
      (p) => p.id === propuestaAceptada.idPropuesta,
    );

    if (!propuesta) {
      this.modalService.abrirError('No se encontró la propuesta');
      return;
    }

    // Crear el contrato
    const contrato: Contrato = {
      id: 0, // El backend lo asignará
      idPropuesta: propuestaAceptada.idPropuesta,
      fechaInicio: '', // El backend lo asignará
      monto: propuesta.montoOfertado,
      estado: EstadoContrato.EN_PROGRESO,
      calificacion: 0,
    };

    this.contratoServicio.registrarContrato(contrato).subscribe({
      next: () => {
        this.modalService.abrirExito('Propuesta aceptada y contrato creado exitosamente');
        this.eliminarGrupoPropuestas(propuestaAceptada.idProyecto);
      },
      error: (error) => {
        console.error('Error al crear el contrato', error);
        this.modalService.abrirError('Error al crear el contrato. Por favor intenta de nuevo');
      },
    });
  }

  protected eliminarGrupoPropuestas(idProyecto: number): void {
    const proyectosActuales = this.proyectosConPropuestas();
    const proyectosActualizados = proyectosActuales.filter((pc) => pc.proyecto.id !== idProyecto);
    this.proyectosConPropuestas.set(proyectosActualizados);
  }
}
