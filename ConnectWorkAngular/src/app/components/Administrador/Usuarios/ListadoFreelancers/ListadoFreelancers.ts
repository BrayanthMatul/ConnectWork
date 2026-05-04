import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { PerfilServicio } from '../../../../services/PerfilServicio';
import { Perfil } from '../../../../models/perfil';
import { TipoUsuario } from '../../../../enums/tipo-usuario';
import { ListaPerfil } from '../../../../shared/ListaPerfil/ListaPerfil';

@Component({
  selector: 'app-listado-freelancers',
  imports: [ListaPerfil],
  templateUrl: './ListadoFreelancers.html',
})
export default class ListadoFreelancers {
  private pefilServicio = inject(PerfilServicio);
  protected perfiles = signal<Perfil[]>([]);
  protected titulo = signal('Freelancers');

  ngOnInit() {
    this.cargarPerfiles();
  }

  private cargarPerfiles() {
    this.pefilServicio.obtenerPerfiles().subscribe({
      next: (perfiles) => {
        this.perfiles.set(
          perfiles.filter((perfil) => perfil.usuario.tipoUsuario === TipoUsuario.FREELANCER),
        );
      },
      error: (error) => {
        console.error('Error al cargar los perfiles:', error);
      },
    });
  }
}
