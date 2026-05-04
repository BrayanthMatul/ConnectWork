import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { LoginServicio } from '../services/LoginServicio';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private loginServicio: LoginServicio,
    private router: Router,
  ) {}

  canActivate(): boolean {
    const token = this.loginServicio.getToken();

    if (token) {
      return true;
    }

    // Si no hay token, redirige a inicio
    this.router.navigate(['/Inicio']);
    return false;
  }
}
