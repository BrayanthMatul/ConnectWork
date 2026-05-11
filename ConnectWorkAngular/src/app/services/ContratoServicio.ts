import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Contrato } from '../models/contrato';
import { Observable } from 'rxjs/internal/Observable';
import { Respuesta } from '../models/respuesta';
import { EstadoContrato } from '../enums/estado-contrato';
import { map } from 'rxjs/internal/operators/map';

@Injectable({
  providedIn: 'root',
})
export class ContratoServicio {
  private url = `${environment.urlBaseApi}api/contrato`;
  private http = inject(HttpClient);

  public registrarContrato(contrato: Contrato): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, contrato);
  }

  public obtenerContratos(): Observable<Contrato[]> {
    return this.http.get<Contrato[]>(this.url);
  }

  public actualizarContrato(contrato: Contrato): Observable<Respuesta> {
    return this.http.put<Respuesta>(this.url, contrato);
  }

  public obtenerContratoPorId(id: number): Observable<Contrato | undefined> {
    return this.obtenerContratos().pipe(map((contratos) => contratos.find((c) => c.id === id)));
  }

  public obtenerContratosPorCliente(idCliente: number): Observable<Contrato[]> {
    return this.obtenerContratos().pipe(
      map((contratos) =>
        contratos.filter((contrato) => contrato.estado !== EstadoContrato.EN_PROGRESO),
      ),
    );
  }

  public obtenerContratosPorFreelancer(idFreelancer: number): Observable<Contrato[]> {
    return this.obtenerContratos().pipe(
      map((contratos) =>
        contratos.filter((contrato) => contrato.estado === EstadoContrato.EN_PROGRESO),
      ),
    );
  }

  public obtenerContratosActivos(): Observable<Contrato[]> {
    return this.obtenerContratos().pipe(
      map((contratos) =>
        contratos.filter((contrato) => contrato.estado === EstadoContrato.EN_PROGRESO),
      ),
    );
  }

  public obtenerContratosFinalizados(): Observable<Contrato[]> {
    return this.obtenerContratos().pipe(
      map((contratos) =>
        contratos.filter((contrato) => contrato.estado === EstadoContrato.COMPLETADO),
      ),
    );
  }
}
