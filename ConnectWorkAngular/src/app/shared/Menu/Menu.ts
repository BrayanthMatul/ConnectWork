import { Component, input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

export interface MenuOpciones {
  etiqueta: string;
  ruta?: string;
  subOpciones?: MenuOpciones[];
}

@Component({
  selector: 'app-menu',
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './Menu.html',
})
export class Menu {
  opciones = input.required<MenuOpciones[]>();
}
