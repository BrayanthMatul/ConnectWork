import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MenuOpciones } from '../../Menu/Menu';

@Component({
  selector: 'app-menu-perfil',
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './MenuPerfil.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MenuPerfil {
  opcionesMenuPerfil = input.required<MenuOpciones[]>();
  cerrarOverlay = input.required<() => void>();
}
