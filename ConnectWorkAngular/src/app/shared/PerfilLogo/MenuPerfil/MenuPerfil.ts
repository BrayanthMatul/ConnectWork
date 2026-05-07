import { ChangeDetectionStrategy, Component, inject, input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MenuOpciones } from '../../Menu/Menu';
import { AuthService } from '../../../services/AuthServicio';

@Component({
  selector: 'app-menu-perfil',
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './MenuPerfil.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MenuPerfil {
  private authServicio = inject(AuthService);
  opcionesMenuPerfil = input.required<MenuOpciones[]>();
  cerrarOverlay = input.required<() => void>();

  protected cerrarSesion() {
    this.authServicio.logout();
    this.cerrarOverlay();
  }
}
