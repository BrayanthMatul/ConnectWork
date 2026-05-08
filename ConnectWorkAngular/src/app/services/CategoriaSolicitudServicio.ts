import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs/internal/Observable';
import { CategoriaSolicitud } from '../models/categoria-solicitud';
import { map } from 'rxjs/internal/operators/map';

@Injectable({
  providedIn: 'root',
})
export class CategoriaSolicitudServicio {
  private url = `${environment.urlBaseApi}api/categoria-solicitud`;
  private http = inject(HttpClient);

  public registrarSolicitud(solicitud: CategoriaSolicitud): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, solicitud);
  }

  public obtenerSolicitudes(): Observable<CategoriaSolicitud[]> {
    return this.http.get<CategoriaSolicitud[]>(this.url);
  }

  public actualizarEstado(id: number, aceptada: boolean): Observable<Respuesta> {
    return this.http.patch<Respuesta>(this.url, { id, activo: aceptada });
  }

  public obtenerCategoriasNoRevisadas(): Observable<CategoriaSolicitud[]> {
    return this.obtenerSolicitudes().pipe(
      map((solicitudes) => solicitudes.filter((solicitud) => !solicitud.revisada)),
    );
  }
}
