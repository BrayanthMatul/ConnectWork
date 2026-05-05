import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs/internal/Observable';
import { SaldoSistema } from '../models/saldo-sistema';

export interface SaldoSistemaResponse {
  registros: SaldoSistema[];
  totalSaldo: number;
}

@Injectable({
  providedIn: 'root',
})
export class SaldoSistemaServicio {
  private url = `${environment.urlBaseApi}api/saldo`;
  private http = inject(HttpClient);

  public registrarNuevoSaldoSistema(saldoSistema: SaldoSistema): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, saldoSistema);
  }

  public obtenerTodosLosRegistros(): Observable<SaldoSistemaResponse> {
    return this.http.get<SaldoSistemaResponse>(this.url);
  }
}
