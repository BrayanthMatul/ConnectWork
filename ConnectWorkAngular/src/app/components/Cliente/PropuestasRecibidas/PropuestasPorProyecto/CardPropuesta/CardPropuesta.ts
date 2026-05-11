import { Component, inject, input, output, signal } from '@angular/core';
import { Propuesta } from '../../../../../models/propuesta';
import { UsuarioServicio } from '../../../../../services/UsuarioServicio';

@Component({
  selector: 'app-card-propuesta',
  imports: [],
  templateUrl: './CardPropuesta.html',
})
export class CardPropuesta {
  private usuarioServicio = inject(UsuarioServicio);
  public propuesta = input.required<Propuesta>();
  protected nombreFreelancer = signal<string>('');
  protected idEliminacion = output<number>();
  protected idAceptacion = output<number>();

  ngOnInit() {
    this.cargarNombreFreelancer();
  }

  private cargarNombreFreelancer(): void {
    this.usuarioServicio.obtenerUsuarioPorId(this.propuesta().idFreelancer).subscribe({
      next: (usuario) => {
        if (usuario) {
          this.nombreFreelancer.set(usuario.username);
        } else {
          this.nombreFreelancer.set('Freelancer desconocido');
        }
      },
      error: (error) => {
        this.nombreFreelancer.set('Error al cargar el nombre del freelancer');
      },
    });
  }

  protected rechazarPropuesta(): void {
    this.idEliminacion.emit(this.propuesta().id);
  }

  protected aceptarPropuesta(): void {
    this.idAceptacion.emit(this.propuesta().id);
  }
}
