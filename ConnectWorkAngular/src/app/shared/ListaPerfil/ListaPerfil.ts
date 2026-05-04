import { ChangeDetectionStrategy, Component, input, signal } from '@angular/core';
import { Perfil } from '../../models/perfil';

@Component({
  selector: 'app-lista-perfil',
  imports: [],
  templateUrl: './ListaPerfil.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListaPerfil {
  public perfiles = input.required<Perfil[]>();
  public titulo = input.required<string>();
  protected listaVacia = signal(false);
}
