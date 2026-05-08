import { Component, signal } from '@angular/core';
import { Habilidad } from '../../../models/habilidad';
import { Filtro } from './Filtro/Filtro';
import { Lista } from './Lista/Lista';

@Component({
  selector: 'app-explorar-proyectos',
  imports: [Filtro, Lista],
  templateUrl: './ExplorarProyectos.html',
})
export default class ExplorarProyectos {
  protected categoriaSeleccionada = signal<number>(0);
  protected habilidadSeleccionada = signal<number>(0);
  protected habilidadesSeleccionadas = signal<Habilidad[]>([]);
  protected habilidadesDeCategoria = signal<Habilidad[]>([]);
}
