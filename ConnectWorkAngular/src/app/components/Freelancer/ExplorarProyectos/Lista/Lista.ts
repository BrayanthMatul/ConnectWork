import { Component, input, signal } from '@angular/core';
import { Proyecto } from '../../../../models/proyecto';
import { CardProyecto } from './CardProyecto/CardProyecto';

@Component({
  selector: 'app-lista',
  imports: [CardProyecto],
  templateUrl: './Lista.html',
  host: {
    class: 'flex-1 flex',
  },
})
export class Lista {
  public proyectos = input.required<Proyecto[]>();
  protected proyectosUno = signal<Proyecto[]>([]);
}
