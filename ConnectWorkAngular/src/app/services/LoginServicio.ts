import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Usuario } from '../models/usuario';
import { environment } from '../../environments/environment';

export interface LoginRequest {
  usernameOrEmail: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  usuario: Usuario;
}

@Injectable({ providedIn: 'root' })
export class LoginServicio {
  private apiUrl = `${environment.urlBaseApi}api/login`;
  private http = inject(HttpClient);

  public login(data: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiUrl, data).pipe(
      tap((resp) => {
        // Guarda el token en localStorage
        localStorage.setItem('jwt', resp.token);
        localStorage.setItem('usuario', JSON.stringify(resp.usuario));
      }),
      catchError(this.mostrarError),
    );
  }

  public logout() {
    localStorage.removeItem('jwt');
    localStorage.removeItem('usuario');
  }

  public getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  public getUsuario(): Usuario | null {
    const usuario = localStorage.getItem('usuario');
    return usuario ? JSON.parse(usuario) : null;
  }

  private mostrarError(error: HttpErrorResponse) {
    return throwError(() => error);
  }
}
