import { Component, computed, inject, output, signal } from '@angular/core';
import { Habilidad } from '../../../../models/habilidad';
import { ModalService } from '../../../../services/ModalService';
import { LoginServicio } from '../../../../services/LoginServicio';
import { CategoriaListaServicio } from '../../../../services/CategoriaListaServicio';

export interface FiltroProyectos {
  rangoMinimo?: number;
  rangoMaximo?: number;
  idCategoria?: number;
  habilidadesSeleccionadas?: Habilidad[];
  rangoActivo: boolean;
  categoriaActiva: boolean;
}
@Component({
  selector: 'app-filtro',
  imports: [],
  templateUrl: './Filtro.html',
})
export class Filtro {
  public filtroActivo = output<FiltroProyectos>();

  protected habilidadSeleccionada = signal<number>(0);
  protected habilidadesSeleccionadas = signal<Habilidad[]>([]);
  protected habilidadesDeCategoria = signal<Habilidad[]>([]);

  protected rangoActivo = signal<boolean>(false);
  protected rangoMinimo = signal<number>(100);
  protected rangoMaximo = signal<number>(2000);

  protected idCategoriaSeleccionada = signal<number>(0);
  protected categoriaActiva = signal<boolean>(false);
  protected categoriasConHabilidades = computed(() =>
    this.categoriaListaServicio.categoriasConHabilidades(),
  );

  private categoriaListaServicio = inject(CategoriaListaServicio);
  private modalService = inject(ModalService);

  ngOnInit() {
    this.categoriaListaServicio.cargarDatos();
    this.filtrarInicial();
  }

  private filtrarInicial() {
    const filtroInicial: FiltroProyectos = {
      rangoActivo: false,
      categoriaActiva: false,
    };

    this.filtroActivo.emit(filtroInicial);
  }

  protected aplicarFiltroRango() {
    this.rangoActivo.set(true);

    const filtro: FiltroProyectos = {
      rangoMinimo: this.rangoMinimo(),
      rangoMaximo: this.rangoMaximo(),
      idCategoria: this.categoriaActiva() ? this.idCategoriaSeleccionada() : undefined,
      habilidadesSeleccionadas: this.habilidadesSeleccionadas(),
      rangoActivo: this.rangoActivo(),
      categoriaActiva: this.categoriaActiva(),
    };

    this.filtroActivo.emit(filtro);
  }

  protected limpiarFiltroRango() {
    this.rangoMinimo.set(100);
    this.rangoMaximo.set(2000);
    this.rangoActivo.set(false);

    const filtro: FiltroProyectos = {
      idCategoria: this.idCategoriaSeleccionada(),
      habilidadesSeleccionadas: this.habilidadesSeleccionadas(),
      rangoActivo: this.rangoActivo(),
      categoriaActiva: this.categoriaActiva(),
    };

    this.filtroActivo.emit(filtro);
  }

  protected aplicarFiltroCategoria() {
    if (this.idCategoriaSeleccionada() !== 0) {
      this.categoriaActiva.set(true);

      const filtro: FiltroProyectos = {
        rangoMinimo: this.rangoMinimo(),
        rangoMaximo: this.rangoMaximo(),
        idCategoria: this.idCategoriaSeleccionada(),
        habilidadesSeleccionadas: this.habilidadesSeleccionadas(),
        rangoActivo: this.rangoActivo(),
        categoriaActiva: this.categoriaActiva(),
      };

      this.filtroActivo.emit(filtro);
    } else {
      this.modalService.abrirAdvertencia(
        'Debe seleccionar una categoría para aplicar el filtro de categoría.',
      );
    }
  }

  protected limpiarFiltroCategoria() {
    this.idCategoriaSeleccionada.set(0);
    this.habilidadSeleccionada.set(0);
    this.habilidadesSeleccionadas.set([]);
    this.habilidadesDeCategoria.set([]);
    this.idCategoriaSeleccionada.set(0);
    this.categoriaActiva.set(false);

    const filtro: FiltroProyectos = {
      rangoMinimo: this.rangoMinimo(),
      rangoMaximo: this.rangoMaximo(),
      rangoActivo: this.rangoActivo(),
      categoriaActiva: this.categoriaActiva(),
    };

    this.filtroActivo.emit(filtro);
  }

  protected limipiarFiltros() {
    this.rangoMinimo.set(100);
    this.rangoMaximo.set(2000);
    this.rangoActivo.set(false);

    this.idCategoriaSeleccionada.set(0);
    this.habilidadSeleccionada.set(0);
    this.habilidadesSeleccionadas.set([]);
    this.habilidadesDeCategoria.set([]);
    this.categoriaActiva.set(false);

    const filtro: FiltroProyectos = {
      rangoMinimo: this.rangoMinimo(),
      rangoMaximo: this.rangoMaximo(),
      idCategoria: undefined,
      habilidadesSeleccionadas: [],
      rangoActivo: false,
      categoriaActiva: false,
    };

    this.filtroActivo.emit(filtro);
  }

  protected actualizarCategoria() {
    this.categoriaActiva.set(true);
    this.actualiazarHabilidades();
  }

  private actualiazarHabilidades() {
    this.habilidadesSeleccionadas.set([]);
    const categoria = this.categoriasConHabilidades().find(
      (c) => c.id === this.idCategoriaSeleccionada(),
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

  toNumber(value: string): number {
    return Number(value);
  }
}
