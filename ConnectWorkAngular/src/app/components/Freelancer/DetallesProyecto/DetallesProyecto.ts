import { Component, computed, inject, input, signal } from '@angular/core';
import { Proyecto } from '../../../models/proyecto';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoriaListaServicio } from '../../../services/CategoriaListaServicio';
import { UsuarioServicio } from '../../../services/UsuarioServicio';
import { ProyectoServicio } from '../../../services/ProyectoServicio';
import { EstadoProyecto } from '../../../enums/estado-proyecto';
import { EstadoPropuesta } from '../../../enums/estado-propuesta';
import { ProyectoHabilidadServicio } from '../../../services/ProyectoHabilidadServicio';
import { FreelancerHabilidadServicio } from '../../../services/FreelancerHabilidadServicio';
import { LoginServicio } from '../../../services/LoginServicio';
import { ModalService } from '../../../services/ModalService';
import { PropuestaServicio } from '../../../services/PropuestaServicio';

@Component({
  selector: 'app-detalles-proyecto',
  imports: [],
  templateUrl: './DetallesProyecto.html',
})
export default class DetallesProyecto {
  private router = inject(Router);
  private activateRoute = inject(ActivatedRoute);
  private proyectoHabilidadServicio = inject(ProyectoHabilidadServicio);
  private categoriaListaServicio = inject(CategoriaListaServicio);
  private proyectoServicio = inject(ProyectoServicio);
  private usuarioServicio = inject(UsuarioServicio);
  private freelancerHabilidadServicio = inject(FreelancerHabilidadServicio);
  private loginServicio = inject(LoginServicio);
  private modalServicio = inject(ModalService);
  private propuestaServicio = inject(PropuestaServicio);

  protected idFreelancer = signal<number>(0);

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
  protected habilidadesFreelancer = signal<number[]>([]);
  protected habilidadesProyecto = signal<number[]>([]);
  protected categoriaConHabilidades = computed(() =>
    this.categoriaListaServicio.categoriasConHabilidades(),
  );

  ngOnInit() {
    const id = this.activateRoute.snapshot.paramMap.get('id');
    this.proyectoServicio.obtenerProyectoPorId(Number(id)).subscribe((proyecto) => {
      if (proyecto) {
        this.proyecto.set(proyecto);
        this.cargarDetalles();
      }
    });

    this.idFreelancer.set(this.loginServicio.getUsuario()!.id);

    // Cargar habilidades del freelancer
    this.freelancerHabilidadServicio
      .obtenerHabilidadesPorFreelancer(this.idFreelancer())
      .subscribe((habilidades) => {
        this.habilidadesFreelancer.set(habilidades.map((h) => h.idHabilidad));
      });
  }

  private cargarDetalles() {
    this.categoriaListaServicio.cargarDatos();

    this.usuarioServicio.obtenerUsuarioPorId(this.proyecto().idCliente).subscribe((usuario) => {
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
      // Guardar los IDs de habilidades del proyecto
      this.habilidadesProyecto.set(habilidadesProyecto.map((h) => h.idHabilidad));

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

  protected postular() {
    if (this.validarPostulacion()) {
      // Verificar si ya existe una propuesta activa del freelancer para este proyecto
      this.propuestaServicio.obtenerPropuestas().subscribe({
        next: (propuestas) => {
          const propuestaActiva = propuestas.find(
            (p) =>
              p.idProyecto === this.proyecto().id &&
              p.idFreelancer === this.idFreelancer() &&
              p.estado !== EstadoPropuesta.RECHAZADA,
          );

          if (propuestaActiva) {
            this.modalServicio.abrirError('Ya se envio una propuesta a este proyecto');
            return;
          }

          // Si no existe propuesta activa, proceder a navegar
          this.router.navigate(['/freelancer-principal/postulacion', this.proyecto().id]);
        },
        error: (err) => {
          this.modalServicio.abrirError('Error al verificar propuestas: ' + err.message);
        },
      });
    }
  }

  private validarPostulacion(): boolean {
    return this.categoriaCorrecta();
  }

  private categoriaCorrecta(): boolean {
    const habilidadesProyectoIds = this.habilidadesProyecto();
    const habilidadesFreelancerIds = this.habilidadesFreelancer();

    if (habilidadesProyectoIds.length > 0) {
      const tieneHabilidadCoincidente = habilidadesFreelancerIds.some((idHabilidad) =>
        habilidadesProyectoIds.includes(idHabilidad),
      );

      if (tieneHabilidadCoincidente) {
        return true;
      } else {
        this.modalServicio.abrirAdvertencia(
          'No te puedes postular, no tienes las habilidades requeridas para este proyecto.',
        );
        return false;
      }
    } else {
      // Si el proyecto NO tiene habilidades asociadas, verificar que la categoría coincida
      const categoriaProyecto = this.proyecto().idCategoria;

      // Obtener la categoría de cada habilidad del freelancer
      const categoriaFreelancer = habilidadesFreelancerIds.some((idHabilidad) => {
        const categoria = this.categoriaListaServicio.categoriaPorIdHabilidad(idHabilidad);
        return categoria && categoria.id === categoriaProyecto;
      });

      if (categoriaFreelancer) {
        return true;
      } else {
        this.modalServicio.abrirAdvertencia(
          'No te puedes postular, no tienes las habilidades requeridas para este proyecto.',
        );
        return false;
      }
    }
  }

  protected regresar() {
    window.history.back();
  }
}
