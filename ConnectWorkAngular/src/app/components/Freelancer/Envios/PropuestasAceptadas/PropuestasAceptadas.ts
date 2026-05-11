import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropuestaServicio } from '../../../../services/PropuestaServicio';
import { Propuesta } from '../../../../models/propuesta';

@Component({
  selector: 'app-propuestas-aceptadas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './PropuestasAceptadas.html',
})
export default class PropuestasAceptadas implements OnInit {
  private propuestaServicio = inject(PropuestaServicio);
  propuestas = signal<Propuesta[]>([]);

  ngOnInit(): void {
    this.propuestaServicio.obtenerPropuestasAceptadas().subscribe((data) => {
      this.propuestas.set(data);
    });
  }
}
