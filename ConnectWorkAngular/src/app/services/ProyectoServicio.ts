import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Proyecto } from '../models/proyecto';
import { Observable } from 'rxjs/internal/Observable';
import { Respuesta } from '../models/respuesta';
import { EstadoProyecto } from '../enums/estado-proyecto';

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

  public actualizarEstadoProyecto(id: number, estado: EstadoProyecto): Observable<Respuesta> {
    return this.http.patch<Respuesta>(this.url, { id, estado });
  }
}
