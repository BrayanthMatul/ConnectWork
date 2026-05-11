import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/internal/operators/map';

import { HabilidadSolicitud } from '../models/habilidad-solicitud';
@Injectable({
  providedIn: 'root',
})
export class HabilidadSolicitudServicio {
  private url = `${environment.urlBaseApi}api/habilidad-solicitud`;
  private http = inject(HttpClient);

  public registrarSolicitud(solicitud: HabilidadSolicitud): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, solicitud);
  }

  public obtenerSolicitudes(): Observable<HabilidadSolicitud[]> {
    return this.http.get<HabilidadSolicitud[]>(this.url);
  }

  public actualizarEstado(id: number, aceptada: boolean): Observable<Respuesta> {
    return this.http.patch<Respuesta>(this.url, { id, activo: aceptada });
  }

  public obtenerHabilidadesNoRevisadas(): Observable<HabilidadSolicitud[]> {
    return this.obtenerSolicitudes().pipe(
      map((solicitudes) => solicitudes.filter((solicitud) => !solicitud.revisada)),
    );
  }
}
