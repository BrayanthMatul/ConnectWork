import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logo',
  imports: [],
  templateUrl: './Logo.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Logo {
  private routeService = inject(Router);

  irInicio() {
    this.routeService.navigate(['']);
  }
}
