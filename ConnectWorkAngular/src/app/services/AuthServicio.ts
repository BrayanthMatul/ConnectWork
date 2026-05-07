import { inject, Injectable } from '@angular/core';
import { LoginRequest, LoginResponse, LoginServicio } from './LoginServicio';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { switchMap, tap, catchError } from 'rxjs/operators';
import { PerfilServicio } from './PerfilServicio';
import { TipoUsuario } from '../enums/tipo-usuario';
import { throwError, of } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private loginServicio = inject(LoginServicio);
  private perfilServicio = inject(PerfilServicio);
  private router = inject(Router);

  public login(data: LoginRequest): Observable<LoginResponse> {
    return this.loginServicio.login(data).pipe(
      switchMap((respuesta) => {
        const tipoUsuario = respuesta.usuario.tipoUsuario;
        if (tipoUsuario === 'ADMINISTRADOR') {
          this.router.navigate(['/administrador-principal']);
          return of(respuesta);
        } else {
          const userId = respuesta.usuario.id;
          return this.perfilServicio.obtenerPerfiles().pipe(
            tap((perfiles) => {
              const perfil = perfiles.find((p) => p.usuario.id === userId);
              const perfilCompleto = perfil?.perfilCompleto;

              if (perfilCompleto) {
                this.navigateToMainPage(tipoUsuario);
              } else {
                this.navigateToDataCompletion(tipoUsuario);
              }
            }),
            switchMap(() => of(respuesta)),
          );
        }
      }),
      catchError((error) => this.mostrarError(error)),
    );
  }

  private navigateToMainPage(tipoUsuario: TipoUsuario): void {
    if (tipoUsuario === 'CLIENTE') {
      this.router.navigate(['/cliente-principal']);
    } else if (tipoUsuario === 'FREELANCER') {
      this.router.navigate(['/freelancer-principal']);
    }
  }

  private navigateToDataCompletion(tipoUsuario: TipoUsuario): void {
    if (tipoUsuario === 'CLIENTE') {
      this.router.navigate(['/datos-iniciales-cliente']);
    } else if (tipoUsuario === 'FREELANCER') {
      this.router.navigate(['/datos-iniciales-freelancer']);
    }
  }

  public logout() {
    this.loginServicio.logout();
    this.router.navigate(['/login']);
  }

  private mostrarError(error: any) {
    return throwError(() => error);
  }
}
