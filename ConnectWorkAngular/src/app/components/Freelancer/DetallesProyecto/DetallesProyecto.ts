import { Component, computed, inject, input, signal } from '@angular/core';
import { Proyecto } from '../../../models/proyecto';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { CategoriaListaServicio } from '../../../services/CategoriaListaServicio';
import { UsuarioServicio } from '../../../services/UsuarioServicio';
import { ProyectoServicio } from '../../../services/ProyectoServicio';
import { EstadoProyecto } from '../../../enums/estado-proyecto';
import { ProyectoHabilidadServicio } from '../../../services/ProyectoHabilidadServicio';

@Component({
  selector: 'app-detalles-proyecto',
  imports: [],
  templateUrl: './DetallesProyecto.html',
})
export default class DetallesProyecto {
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);
  private proyectoHabilidadServicio = inject(ProyectoHabilidadServicio);
  private categoriaListaServicio = inject(CategoriaListaServicio);
  private proyectoServicio = inject(ProyectoServicio);
  private usuarioServicio = inject(UsuarioServicio);
  public proyecto = signal<Proyecto>({
    id: 0,
    idCliente: 0,
    idCategoria: 0,
    titulo: 'No encontrado',
    descripcion: '',
    presupuestoMaximo: 0,
    fechaLimite: '',
    estado: EstadoProyecto.ABIERTO,
  });
  protected cliente = signal<string>('');
  protected categoria = signal<string>('');
  protected habilidades = signal<string[]>([]);
  protected categoriaConHabilidades = computed(() =>
    this.categoriaListaServicio.categoriasConHabilidades(),
  );

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.proyectoServicio.obtenerProyectoPorId(Number(id)).subscribe((proyecto) => {
      if (proyecto) {
        this.proyecto.set(proyecto);
        console.log('Proyecto obtenido:', proyecto);
        console.log('Proyecto ya seteado:', this.proyecto());
        this.cargarDetalles();
      }
    });
  }

  private cargarDetalles() {
    this.categoriaListaServicio.cargarDatos();
    console.log('Cargando proyecto ', this.proyecto());

    this.usuarioServicio.obtenerUsuarioPorId(this.proyecto().idCliente).subscribe((usuario) => {
      console.log('Usuario obtenido:', usuario);
      if (usuario === undefined) {
        this.cliente.set('Desconocido');
      } else {
        this.cliente.set(usuario.nombreCompleto);
      }
    });

    this.proyectoHabilidadServicio.obtenerProyectoHabilidades().subscribe((proyectoHabilidades) => {
      const habilidadesProyecto = proyectoHabilidades.filter(
        (ph) => ph.idProyecto === this.proyecto().id,
      );
      const nombresHabilidades = habilidadesProyecto.map((ph) => {
        const categoria = this.categoriaConHabilidades().find(
          (cat) => cat.id === this.proyecto().idCategoria,
        );
        if (categoria) {
          const habilidad = categoria.habilidades.find((h) => h.id === ph.idHabilidad);
          return habilidad ? habilidad.nombre : 'Desconocida';
        }
        return 'Desconocida';
      });
      this.habilidades.set(nombresHabilidades);
    });

    const categoria = this.categoriaConHabilidades().find(
      (cat) => cat.id === this.proyecto().idCategoria,
    );
    if (categoria) {
      this.categoria.set(categoria.nombre);
    } else {
      this.categoria.set('Desconocida');
    }
  }

  protected postular() {}

  protected regresar() {
    window.history.back();
  }
}
