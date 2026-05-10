import { Component, inject, signal } from '@angular/core';
import { ModalService } from '../../../services/ModalService';
import { HabilidadServicio } from '../../../services/HabilidadServicio';
import { CategoriaServicio } from '../../../services/CategoriaServicio';
import { Categoria } from '../../../models/categoria';
import { Habilidad } from '../../../models/habilidad';

@Component({
  selector: 'app-solicitar-habilidad',
  imports: [],
  templateUrl: './SolicitarHabilidad.html',
})
export default class SolicitarHabilidad {
  protected nombre = signal<string>('');
  protected descripcion = signal<string>('');
  protected categoriasServicio = inject(CategoriaServicio);
  private habilidadServicio = inject(HabilidadServicio);
  private modalServicio = inject(ModalService);
  protected categorias = signal<Categoria[]>([]);
  protected categoriaSeleccionada = signal<number>(0);

  ngOnInit() {
    this.cargarCategorias();
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
    const habilidad: Habilidad = {
      id: 0,
      idCategoria: this.categoriaSeleccionada(),
      nombre: this.nombre(),
      descripcion: this.descripcion(),
      activo: true,
    };

    this.habilidadServicio.registrarHabilidad(habilidad).subscribe({
      next: (respuesta) => {
        this.modalServicio.abrirExito(respuesta.valor);
        this.limpiarFormulario();
      },
      error: (error) => {
        this.modalServicio.abrirError(error.error.valor);
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
