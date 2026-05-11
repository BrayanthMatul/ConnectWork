import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Propuesta } from '../models/propuesta';
import { Observable } from 'rxjs/internal/Observable';
import { Respuesta } from '../models/respuesta';
import { EstadoPropuesta } from '../enums/estado-propuesta';
import { map } from 'rxjs/internal/operators/map';

@Injectable({
  providedIn: 'root',
})
export class PropuestaServicio {
  private url = `${environment.urlBaseApi}api/propuesta`;
  private http = inject(HttpClient);

  public registrarPropuesta(propuesta: Propuesta): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, propuesta);
  }

  public obtenerPropuestas(): Observable<Propuesta[]> {
    return this.http.get<Propuesta[]>(this.url);
  }

  public actualizarEstadoPropuesta(request: {
    idPropuesta: number;
    nuevoEstado: EstadoPropuesta;
  }): Observable<Respuesta> {
    return this.http.patch<Respuesta>(this.url, request);
  }

  public obtenerPropuestasPorFreelancer(idFreelancer: number): Observable<Propuesta[]> {
    return this.obtenerPropuestas().pipe(
      map((propuestas) =>
        propuestas.filter((propuesta) => propuesta.idFreelancer === idFreelancer),
      ),
    );
  }

  public obtenerPropuestasPendientes(): Observable<Propuesta[]> {
    return this.obtenerPropuestas().pipe(
      map((propuestas) =>
        propuestas.filter((propuesta) => propuesta.estado === EstadoPropuesta.PENDIENTE),
      ),
    );
  }

  public obtenerPropuestasAceptadas(): Observable<Propuesta[]> {
    return this.obtenerPropuestas().pipe(
      map((propuestas) =>
        propuestas.filter((propuesta) => propuesta.estado === EstadoPropuesta.ACEPTADA),
      ),
    );
  }

  public obtenerPropuestasRechazadas(): Observable<Propuesta[]> {
    return this.obtenerPropuestas().pipe(
      map((propuestas) =>
        propuestas.filter((propuesta) => propuesta.estado === EstadoPropuesta.RECHAZADA),
      ),
    );
  }

  public obtenerPropuestaPorId(id: number): Observable<Propuesta | undefined> {
    return this.obtenerPropuestas().pipe(map((propuestas) => propuestas.find((p) => p.id === id)));
  }
}
