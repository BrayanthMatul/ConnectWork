import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Perfil } from '../models/perfil';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root',
})
export class PerfilServicio {
  private url = `${environment.urlBaseApi}api/perfil`;
  private http = inject(HttpClient);

  public registrarPerfil(perfil: Perfil): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, perfil);
  }

  public obtenerPerfiles(): Observable<Perfil[]> {
    return this.http.get<Perfil[]>(this.url);
  }

  public actualizarPerfil(perfil: Perfil): Observable<Respuesta> {
    return this.http.put<Respuesta>(this.url, perfil);
  }

  public actualizarEstadoPerfil(id: number, activo: boolean): Observable<Respuesta> {
    return this.http.patch<Respuesta>(this.url, { id, activo });
  }
}
