import { inject, Injectable } from '@angular/core';
import { Respuesta } from '../models/respuesta';
import { Usuario } from '../models/usuario';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class UsuarioServicio {
  private url = `${environment.urlBaseApi}api/usuario`;
  private http = inject(HttpClient);

  public registrarUsuario(usuario: Usuario): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, usuario);
  }

  public obtenerUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.url);
  }

  public obtenerUsuarioPorId(id: number): Observable<Usuario | undefined> {
    return this.obtenerUsuarios().pipe(map((usuarios) => usuarios.find((u) => u.id === id)));
  }
}
