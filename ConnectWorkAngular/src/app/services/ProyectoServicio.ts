import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Proyecto } from '../models/proyecto';
import { Observable } from 'rxjs/internal/Observable';
import { Respuesta } from '../models/respuesta';
import { EstadoProyecto } from '../enums/estado-proyecto';
import { map } from 'rxjs/internal/operators/map';
import { EstadoProyectoRequest } from '../models/estado-proyecto-request';

@Injectable({
  providedIn: 'root',
})
export class ProyectoServicio {
  private url = `${environment.urlBaseApi}api/proyecto`;
  private http = inject(HttpClient);

  public registrarProyecto(proyecto: Proyecto): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, proyecto);
  }

  public obtenerProyectos(): Observable<Proyecto[]> {
    return this.http.get<Proyecto[]>(this.url);
  }

  public actualizarProyecto(proyecto: Proyecto): Observable<Respuesta> {
    return this.http.put<Respuesta>(this.url, proyecto);
  }

  public actualizarEstadoProyecto(request: EstadoProyectoRequest): Observable<Respuesta> {
    return this.http.patch<Respuesta>(this.url, request);
  }

  public obtenerProyectosActivosPorCliente(idCliente: number): Observable<Proyecto[]> {
    return this.obtenerProyectos().pipe(
      map((proyectos) =>
        proyectos.filter(
          (proyecto) =>
            proyecto.idCliente === idCliente &&
            proyecto.estado !== EstadoProyecto.FINALIZADO &&
            proyecto.estado !== EstadoProyecto.CANCELADO,
        ),
      ),
    );
  }

  public obtenerProyectosAbiertos(): Observable<Proyecto[]> {
    return this.obtenerProyectos().pipe(
      map((proyectos) =>
        proyectos.filter((proyecto) => proyecto.estado === EstadoProyecto.ABIERTO),
      ),
    );
  }
}
