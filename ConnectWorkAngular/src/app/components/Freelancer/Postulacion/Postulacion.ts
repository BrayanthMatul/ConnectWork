import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalService } from '../../../services/ModalService';
import { LoginServicio } from '../../../services/LoginServicio';
import { PropuestaServicio } from '../../../services/PropuestaServicio';
import { EstadoPropuesta } from '../../../enums/estado-propuesta';
import { Propuesta } from '../../../models/propuesta';

@Component({
  selector: 'app-postulacion',
  imports: [],
  templateUrl: './Postulacion.html',
})
export default class Postulacion {
  private router = inject(Router);
  private activateRoute = inject(ActivatedRoute);
  private loginServicio = inject(LoginServicio);
  private modalServicio = inject(ModalService);
  private propuestaServicio = inject(PropuestaServicio);
  private idProyecto = signal<number>(0);
  private idFreelancer = signal<number>(0);

  protected montoOfertado = signal<number>(0);
  protected mensaje = signal<string>('');
  protected plazoEntrega = signal<number>(0);

  ngOnInit() {
    const id = this.activateRoute.snapshot.paramMap.get('id');
    this.idProyecto.set(Number(id));
    this.idFreelancer.set(this.loginServicio.getUsuario()!.id);
  }

  public registrarPostulacion() {
    const postulacion: Propuesta = {
      id: 0, // El ID se asigna en el backend
      idProyecto: this.idProyecto(),
      idFreelancer: this.idFreelancer(),
      montoOfertado: this.montoOfertado(),
      mensaje: this.mensaje(),
      fechaHora: '', // Se genera en el backend
      plazoEntrega: this.plazoEntrega(),
      estado: EstadoPropuesta.PENDIENTE,
    };

    this.propuestaServicio.registrarPropuesta(postulacion).subscribe({
      next: () => {
        this.modalServicio.abrirExito('Propuesta registrada exitosamente');
        this.router.navigate(['freelancer-principal/explorar-proyectos']);
      },
      error: (err) => {
        this.modalServicio.abrirError(
          'Error al registrar la propuesta: ' + err.error?.error || err.message,
        );
      },
    });
  }
}
