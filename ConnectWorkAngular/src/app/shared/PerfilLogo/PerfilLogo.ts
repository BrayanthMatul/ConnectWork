import { Component, input, signal, ViewChild, ElementRef } from '@angular/core';
import { OverlayModule } from '@angular/cdk/overlay';
import { MenuPerfil } from './MenuPerfil/MenuPerfil';
import { MenuOpciones } from '../Menu/Menu';

@Component({
  selector: 'app-perfil-logo',
  imports: [OverlayModule, MenuPerfil],
  templateUrl: './PerfilLogo.html',
})
export class PerfilLogo {
  @ViewChild('perfilButton') perfilButton!: ElementRef;

  opcionesMenuPerfil = input.required<MenuOpciones[]>();
  isOpen = signal(false);

  toggleMenu() {
    this.isOpen.update((v) => !v);
  }

  cerrarMenu() {
    this.isOpen.set(false);
  }
}
