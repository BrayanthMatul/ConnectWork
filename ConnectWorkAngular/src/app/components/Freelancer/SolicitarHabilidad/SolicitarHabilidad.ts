import { Component, inject, signal } from '@angular/core';
import { ModalService } from '../../../services/ModalService';
import { CategoriaServicio } from '../../../services/CategoriaServicio';
import { HabilidadSolicitudServicio } from '../../../services/HabilidadSolicitudServicio';
import { Categoria } from '../../../models/categoria';
import { HabilidadSolicitud } from '../../../models/habilidad-solicitud';
import { LoginServicio } from '../../../services/LoginServicio';

@Component({
  selector: 'app-solicitar-habilidad',
  imports: [],
  templateUrl: './SolicitarHabilidad.html',
})
export default class SolicitarHabilidad {
  protected nombre = signal<string>('');
  protected descripcion = signal<string>('');
  protected categoriasServicio = inject(CategoriaServicio);
  private modalServicio = inject(ModalService);
  private loginServicio = inject(LoginServicio);
  private habilidadSolicitudServicio = inject(HabilidadSolicitudServicio);
  protected categorias = signal<Categoria[]>([]);
  protected categoriaSeleccionada = signal<number>(0);
  private idFreelancer = signal<number>(0);

  ngOnInit() {
    this.cargarCategorias();
    const idFreelancer = this.loginServicio.getUsuario()?.id;
    if (idFreelancer) {
      this.idFreelancer.set(idFreelancer);
    } else {
      this.modalServicio.abrirError('No se pudo obtener la información del freelancer');
    }
  }

  private cargarCategorias() {
    this.categoriasServicio.obtenerCategorias().subscribe({
      next: (categorias) => {
        this.categorias.set(categorias.filter((c) => c.activo));
      },
      error: (error) => {
        this.modalServicio.abrirError('Error al cargar las categorías');
      },
    });
  }

  protected registrarSolicitud() {
    const habilidad: HabilidadSolicitud = {
      id: 0,
      idCategoria: this.categoriaSeleccionada(),
      nombre: this.nombre(),
      descripcion: this.descripcion(),
      idFreelancer: this.idFreelancer(),
      aceptada: false,
      revisada: false,
    };

    this.habilidadSolicitudServicio.registrarSolicitud(habilidad).subscribe({
      next: (respuesta) => {
        this.modalServicio.abrirExito('Solicitud de habilidad registrada correctamente');
        this.limpiarFormulario();
      },
      error: (error) => {
        this.modalServicio.abrirError('Error al registrar la solicitud de habilidad');
      },
    });
  }

  protected toNumber(value: string): number {
    return Number(value);
  }

  private limpiarFormulario() {
    this.nombre.set('');
    this.descripcion.set('');
  }
}
