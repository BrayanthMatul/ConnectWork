import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { ProyectoHabilidad } from '../models/proyecto-habilidad';

@Injectable({
  providedIn: 'root',
})
export class ProyectoHabilidadServicio {
  private url = `${environment.urlBaseApi}api/proyecto-habilidad`;
  private http = inject(HttpClient);

  public obtenerProyectoHabilidades(): Observable<ProyectoHabilidad[]> {
    return this.http.get<ProyectoHabilidad[]>(this.url);
  }
}
