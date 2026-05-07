import { Component, inject, input, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Logo } from '../../shared/Logo/Logo';
import { FreelancerServicio } from '../../services/FreelancerServicio';
import { LoginServicio } from '../../services/LoginServicio';
import { ModalService } from '../../services/ModalService';
import { Freelancer } from '../../models/freelancer';

@Component({
  selector: 'app-datos-iniciales-freelancer',
  imports: [Logo],
  templateUrl: './DatosInicialesFreelancer.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class DatosInicialesFreelancer {
  protected biografia = signal<string>('');
  protected nivelExperiencia = signal<string>('');
  protected tarifaHora = signal<number>(0);

  private freelancerServicio = inject(FreelancerServicio);
  private loginServicio = inject(LoginServicio);
  private modalService = inject(ModalService);
  private router = inject(Router);

  protected onSubmit() {
    const usuario = this.loginServicio.getUsuario();

    if (!usuario) {
      this.modalService.abrirError('Usuario no encontrado');
      return;
    }

    // Construir Freelancer sin perfil
    const freelancer: Freelancer = {
      idFreelancer: usuario.id,
      biografia: this.biografia(),
      nivelExperiencia: this.nivelExperiencia(),
      calificacion: 0,
      tarifaHora: this.tarifaHora(),
      perfil: null as any,
    };

    // Enviar freelancer al backend
    this.freelancerServicio.registrarDatosInicialesFreelancer(freelancer).subscribe({
      next: (respuesta) => {
        this.modalService.abrirExito(respuesta.valor);
        this.router.navigate(['/freelancer-principal']);
      },
      error: (error) => {
        console.log('Error al registrar datos iniciales:', error);
        const mensaje = error.error?.valor || 'Error al registrar datos';
        this.modalService.abrirError(mensaje);
      },
    });
  }
}
