import { Component, inject, signal } from '@angular/core';
import { Usuario } from '../../../../models/usuario';
import { UsuarioServicio } from '../../../../services/UsuarioServicio';
import { TipoUsuario } from '../../../../enums/tipo-usuario';
import { ModalService } from '../../../../services/ModalService';

@Component({
  selector: 'app-lista-administradores',
  imports: [],
  templateUrl: './ListaAdministradores.html',
})
export default class ListaAdministradores {
  protected administradores = signal<Usuario[]>([]);
  private usuarioServicio = inject(UsuarioServicio);
  private modalService = inject(ModalService);
  protected listaVacia = signal(false);

  ngOnInit() {
    this.cargarAdministradores();
  }

  private cargarAdministradores() {
    this.usuarioServicio.obtenerUsuarios().subscribe({
      next: (usuarios) => {
        if (usuarios.length === 0) {
          this.listaVacia.set(true);
          return;
        }
        this.administradores.set(
          usuarios.filter((usuario) => usuario.tipoUsuario === TipoUsuario.ADMINISTRADOR),
        );
      },
      error: (error) => {
        this.modalService.abrirError(
          'Error al cargar los administradores. Por favor, inténtelo de nuevo más tarde.',
        );
      },
    });
  }
}
