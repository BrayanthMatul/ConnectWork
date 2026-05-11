import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropuestaServicio } from '../../../../services/PropuestaServicio';
import { Propuesta } from '../../../../models/propuesta';

@Component({
  selector: 'app-propuestas-pendientes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './PropuestasPendientes.html',
})
export default class PropuestasPendientes implements OnInit {
  private propuestaServicio = inject(PropuestaServicio);
  propuestas = signal<Propuesta[]>([]);

  ngOnInit(): void {
    this.propuestaServicio.obtenerPropuestasPendientes().subscribe((data) => {
      this.propuestas.set(data);
    });
  }
}
