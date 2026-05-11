import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropuestaServicio } from '../../../../services/PropuestaServicio';
import { Propuesta } from '../../../../models/propuesta';

@Component({
  selector: 'app-propuestas-rechazadas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './PropuestasRechazadas.html',
})
export default class PropuestasRechazadas implements OnInit {
  private propuestaServicio = inject(PropuestaServicio);
  propuestas = signal<Propuesta[]>([]);

  ngOnInit(): void {
    this.propuestaServicio.obtenerPropuestasRechazadas().subscribe((data) => {
      this.propuestas.set(data);
    });
  }
}
