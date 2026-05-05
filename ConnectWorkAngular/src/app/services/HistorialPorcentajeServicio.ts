import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HistorialPorcentajeComision } from '../models/historial-porcentaje-comision';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class HistorialPorcentajeServicio {
  private url = `${environment.urlBaseApi}api/historial-comision`;
  private http = inject(HttpClient);

  public registrarNuevoHistorial(historial: HistorialPorcentajeComision): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, historial);
  }

  public obtenerTodoElHistorial(): Observable<HistorialPorcentajeComision[]> {
    return this.http.get<HistorialPorcentajeComision[]>(this.url);
  }

  public obtenerUltimoHistorial(): Observable<HistorialPorcentajeComision | null> {
    return this.obtenerTodoElHistorial().pipe(
      map((historiales: HistorialPorcentajeComision[]) => {
        if (historiales && historiales.length > 0) {
          return historiales[historiales.length - 1];
        }
        return null;
      }),
    );
  }
}
