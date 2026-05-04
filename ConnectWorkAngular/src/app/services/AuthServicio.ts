import { inject, Injectable } from '@angular/core';
import { LoginRequest, LoginResponse, LoginServicio } from './LoginServicio';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { tap } from 'rxjs/internal/operators/tap';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private loginServicio = inject(LoginServicio);
  private router = inject(Router);

  public login(data: LoginRequest): Observable<LoginResponse> {
    return this.loginServicio.login(data).pipe(
      tap((resp) => {
        // Redirigir según tipo de usuario
        const tipoUsuario = resp.usuario.tipoUsuario;
        if (tipoUsuario === 'ADMINISTRADOR') {
          this.router.navigate(['/administrador-principal']);
        } else if (tipoUsuario === 'CLIENTE') {
          this.router.navigate(['/cliente-principal']);
        } else if (tipoUsuario === 'FREELANCER') {
          this.router.navigate(['/freelancer-principal']);
        }
      }),
    );
  }
}
