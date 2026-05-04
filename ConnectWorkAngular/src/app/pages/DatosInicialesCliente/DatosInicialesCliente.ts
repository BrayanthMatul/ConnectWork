import { Component, inject, input, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Logo } from '../../shared/Logo/Logo';

@Component({
  selector: 'app-datos-iniciales-cliente',
  imports: [Logo],
  templateUrl: './DatosInicialesCliente.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export default class DatosInicialesCliente {
  protected id_cliente = input.required<number>();
  protected descripcion = signal<string>('');
  protected sector = signal<string>('');
  protected sitio_web = signal<string>('');

  protected onSubmit() {}
}
