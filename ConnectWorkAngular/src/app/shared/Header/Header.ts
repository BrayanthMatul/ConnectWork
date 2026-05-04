import { Component, input } from '@angular/core';
import { Logo } from '../Logo/Logo';
import { Menu, MenuOpciones } from '../Menu/Menu';
import { PerfilLogo } from '../PerfilLogo/PerfilLogo';

@Component({
  selector: 'app-header',
  imports: [Logo, Menu, PerfilLogo],
  templateUrl: './Header.html',
})
export class Header {
  public opcionesMenu = input.required<MenuOpciones[]>();
  public opcionesMenuPerfil = input.required<MenuOpciones[]>();
}
