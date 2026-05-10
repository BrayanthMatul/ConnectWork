import { Component, computed, inject, signal } from '@angular/core';
import { EstadoProyecto } from '../../../../enums/estado-proyecto';
import { Categoria } from '../../../../models/categoria';
import { Habilidad } from '../../../../models/habilidad';
import { CategoriaListaServicio } from '../../../../services/CategoriaListaServicio';
import { LoginServicio } from '../../../../services/LoginServicio';
import { Proyecto } from '../../../../models/proyecto';
import { ProyectoHabilidad } from '../../../../models/proyecto-habilidad';
import { ProyectoServicio } from '../../../../services/ProyectoServicio';
import { ModalService } from '../../../../services/ModalService';

@Component({
  selector: 'app-publicar-proyecto',
  imports: [],
  templateUrl: './PublicarProyecto.html',
})
export default class PublicarProyecto {
  private categoriaListaServicio = inject(CategoriaListaServicio);
  private loginServicio = inject(LoginServicio);
  private proyectoServicio = inject(ProyectoServicio);
  private modalService = inject(ModalService);

  protected idCliente = signal<number>(0);

  protected titulo = signal<string>('');
  protected descripcion = signal<string>('');
  protected presupuestoMaximo = signal<number>(0);
  protected fechaLimite = signal<string>('');
  protected estado = signal<EstadoProyecto>(EstadoProyecto.ABIERTO);

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
    const usuario = this.loginServicio.getUsuario();
    if (usuario) {
      this.idCliente.set(usuario.id);
    }
  }

  protected publicarProyecto() {
    const habilidadesConstruidas = this.construirHabilidadesProyecto();
    const nuevoProyecto: Proyecto = {
      id: 0, // El ID se asignará en el backend
      idCliente: this.idCliente(),
      idCategoria: this.idCategoria(),
      titulo: this.titulo(),
      descripcion: this.descripcion(),
      presupuestoMaximo: this.presupuestoMaximo(),
      fechaLimite: this.fechaLimite(),
      estado: this.estado(),
      habilidades: habilidadesConstruidas,
    };

    this.proyectoServicio.registrarProyecto(nuevoProyecto).subscribe({
      next: (respuesta) => {
        this.modalService.abrirExito(respuesta.valor);
        this.limpiarFormulario();
      },
      error: (error) => {
        this.modalService.abrirError(error.error?.valor || 'Error al publicar el proyecto');
      },
    });
  }

  private construirHabilidadesProyecto(): ProyectoHabilidad[] {
    return this.habilidadesSeleccionadas().map((habilidad) => ({
      idProyecto: 0, // El ID se asignará en el backend
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

  private limpiarFormulario() {
    this.titulo.set('');
    this.descripcion.set('');
    this.presupuestoMaximo.set(0);
    this.fechaLimite.set('');
    this.categoriaSeleccionada.set(0);
    this.habilidadesSeleccionadas.set([]);
    this.habilidadesDeCategoria.set([]);
  }
}
