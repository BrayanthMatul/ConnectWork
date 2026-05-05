import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs/internal/Observable';
import { Categoria } from '../models/categoria';
import { Habilidad } from '../models/habilidad';

@Injectable({
  providedIn: 'root',
})
export class HabilidadServicio {
  private url = `${environment.urlBaseApi}api/habilidad`;
  private http = inject(HttpClient);

  public registrarHabilidad(habilidad: Habilidad): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, habilidad);
  }

  public obtenerHabilidades(): Observable<Habilidad[]> {
    return this.http.get<Habilidad[]>(this.url);
  }

  public actualizarHabilidad(habilidad: Habilidad): Observable<Respuesta> {
    return this.http.put<Respuesta>(this.url, habilidad);
  }

  public actualizarEstadoHabilidad(id: number, activo: boolean): Observable<Respuesta> {
    return this.http.patch<Respuesta>(this.url, { id, activo });
  }
}
