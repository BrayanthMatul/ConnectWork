import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs/internal/Observable';
import { Categoria } from '../models/categoria';

@Injectable({
  providedIn: 'root',
})
export class CategoriaServicio {
  private url = `${environment.urlBaseApi}api/categoria`;
  private http = inject(HttpClient);

  public registrarCategoria(categoria: Categoria): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, categoria);
  }

  public obtenerCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.url);
  }

  public actualizarCategoria(categoria: Categoria): Observable<Respuesta> {
    return this.http.put<Respuesta>(this.url, categoria);
  }

  public actualizarEstadoCategoria(id: number, activo: boolean): Observable<Respuesta> {
    return this.http.patch<Respuesta>(this.url, { id, activo });
  }
}
