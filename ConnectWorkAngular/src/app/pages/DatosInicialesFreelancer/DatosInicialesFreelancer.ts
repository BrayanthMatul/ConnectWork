import { Component, computed, inject, input, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Logo } from '../../shared/Logo/Logo';
import { FreelancerServicio } from '../../services/FreelancerServicio';
import { LoginServicio } from '../../services/LoginServicio';
import { ModalService } from '../../services/ModalService';
import { Freelancer } from '../../models/freelancer';
import { Habilidad } from '../../models/habilidad';
import { CategoriaListaServicio } from '../../services/CategoriaListaServicio';
import { FreelancerHabilidad } from '../../models/freelancer-habilidad';

@Component({
  selector: 'app-datos-iniciales-freelancer',
  imports: [Logo],
  templateUrl: './DatosInicialesFreelancer.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class DatosInicialesFreelancer {
  private categoriaListaServicio = inject(CategoriaListaServicio);
  protected biografia = signal<string>('');
  protected nivelExperiencia = signal<string>('');
  protected tarifaHora = signal<number>(0);

  private freelancerServicio = inject(FreelancerServicio);
  private loginServicio = inject(LoginServicio);
  private modalService = inject(ModalService);
  private router = inject(Router);

  protected categoriasConHabilidades = computed(() =>
    this.categoriaListaServicio.categoriasConHabilidades(),
  );

  protected idCategoria = signal<number>(0);
  protected categoriaSeleccionada = signal<number>(0);
  protected habilidadSeleccionada = signal<number>(0);
  protected habilidadesSeleccionadas = signal<Habilidad[]>([]);
  protected habilidadesDeCategoria = signal<Habilidad[]>([]);

  ngOnInit() {
    this.categoriaListaServicio.cargarDatos();
  }

  protected onSubmit() {
    const usuario = this.loginServicio.getUsuario();

    if (!usuario) {
      this.modalService.abrirError('Usuario no encontrado');
      return;
    }

    const habilidadesFreelancer = this.construirHabilidadesFreelancer();
    // Construir Freelancer sin perfil
    const freelancer: Freelancer = {
      idFreelancer: usuario.id,
      biografia: this.biografia(),
      nivelExperiencia: this.nivelExperiencia(),
      calificacion: 0,
      tarifaHora: this.tarifaHora(),
      perfil: null as any,
      habilidades: habilidadesFreelancer,
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

  private construirHabilidadesFreelancer(): FreelancerHabilidad[] {
    return this.habilidadesSeleccionadas().map((habilidad) => ({
      idFreelancer: 0, // El ID se asignará en el backend
      idHabilidad: habilidad.id,
    }));
  }

  protected actualizarCategoria() {
    this.idCategoria.set(this.categoriaSeleccionada());
    this.actualiazarHabilidades();
  }

  private actualiazarHabilidades() {
    this.habilidadesSeleccionadas.set([]);
    const categoria = this.categoriasConHabilidades().find(
      (c) => c.id === this.categoriaSeleccionada(),
    );
    if (categoria) {
      this.habilidadesDeCategoria.set(categoria.habilidades);
    }
  }

  protected agregarHabilidad() {
    const idHabilidad = this.habilidadSeleccionada();
    if (idHabilidad) {
      const habilidad = this.habilidadesDeCategoria().find((h) => h.id === idHabilidad);
      if (habilidad) {
        this.habilidadesSeleccionadas.update((habilidades) => [...habilidades, habilidad]);
        this.habilidadesDeCategoria.update((habilidades) =>
          habilidades.filter((h) => h.id !== idHabilidad),
        );
        this.habilidadSeleccionada.set(0);
      }
    }
  }

  protected removerHabilidad(id: number) {
    const habilidad = this.habilidadesSeleccionadas().find((h) => h.id === id);
    if (habilidad) {
      this.habilidadesSeleccionadas.update((habilidades) => habilidades.filter((h) => h.id !== id));
      this.habilidadesDeCategoria.update((habilidades) => [...habilidades, habilidad]);
      this.habilidadSeleccionada.set(0);
    }
  }

  protected toNumber(value: string): number {
    return Number(value);
  }
}
