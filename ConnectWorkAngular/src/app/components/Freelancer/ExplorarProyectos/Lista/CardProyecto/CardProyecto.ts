import { Component, inject, input } from '@angular/core';
import { Proyecto } from '../../../../../models/proyecto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-card-proyecto',
  imports: [],
  templateUrl: './CardProyecto.html',
})
export class CardProyecto {
  private router = inject(Router);

  public proyecto = input.required<Proyecto>();

  verDetalles() {
    this.router.navigate(['/freelancer-principal/detalles-proyecto', this.proyecto().id]);
  }
}
