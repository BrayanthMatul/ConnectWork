
import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Usuario } from '../models/usuario';
import { environment } from '../../environments/environment';

export interface LoginRequest {
	username: string;
	password: string;
}


export interface LoginResponse {
	token: string;
	usuario: Usuario;
}

@Injectable({ providedIn: 'root' })
export class LoginServicio {
    private urlBaseApi = environment.urlBaseApi;
	private apiUrl = `${this.urlBaseApi}api/login`;
    private http = inject(HttpClient);

	login(data: LoginRequest): Observable<LoginResponse> {
		return this.http.post<LoginResponse>(this.apiUrl, data).pipe(
			tap(resp => {
				// Guarda el token en localStorage
				localStorage.setItem('jwt', resp.token);
				localStorage.setItem('usuario', JSON.stringify(resp.usuario));
			}),
			catchError(this.handleError)
		);
	}

	logout() {
		localStorage.removeItem('jwt');
		localStorage.removeItem('usuario');
	}

	getToken(): string | null {
		return localStorage.getItem('jwt');
	}

	getUsuario(): Usuario | null {
		const usuario = localStorage.getItem('usuario');
		return usuario ? JSON.parse(usuario) : null;
	}

	private handleError(error: HttpErrorResponse) {
		let msg = 'Error desconocido';
		if (error.error instanceof ErrorEvent) {
			msg = `Error: ${error.error.message}`;
		} else if (error.error && error.error.error) {
			msg = error.error.error;
		}
		return throwError(() => msg);
	}
}
