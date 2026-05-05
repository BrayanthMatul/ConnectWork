import { Component, inject, signal } from '@angular/core';
import { ModalService } from '../../../../services/ModalService';
import { HabilidadServicio } from '../../../../services/HabilidadServicio';
import { Habilidad } from '../../../../models/habilidad';
import { Categoria } from '../../../../models/categoria';
import { CategoriaServicio } from '../../../../services/CategoriaServicio';

@Component({
  selector: 'app-registrar-habilidad',
  imports: [],
  templateUrl: './RegistrarHabilidad.html',
})
export default class RegistrarHabilidad {
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

  protected registrarHabilidad() {
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
