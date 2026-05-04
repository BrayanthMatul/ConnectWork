import { Component, inject, input, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Logo } from '../../shared/Logo/Logo';

@Component({
  selector: 'app-datos-iniciales-freelancer',
  imports: [Logo],
  templateUrl: './DatosInicialesFreelancer.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export class DatosInicialesFreelancer {
  protected idFreelacer = input.required<number>();
  protected biografia = signal<string>('');
  protected nivleExperiencia = signal<string>('');
  protected tarifaHora = signal<number>(0);

  protected onSubmit() {}
}
