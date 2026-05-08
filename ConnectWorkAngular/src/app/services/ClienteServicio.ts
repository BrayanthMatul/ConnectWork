import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Cliente } from '../models/cliente';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs/internal/Observable';
import { SaldoRequest } from '../models/saldo-request';

@Injectable({
  providedIn: 'root',
})
export class ClienteServicio {
  private url = `${environment.urlBaseApi}api/cliente`;
  private http = inject(HttpClient);

  public registrarDatosInicialesCliente(cliente: Cliente): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, cliente);
  }

  public agregarSaldoCliente(saldoRequest: SaldoRequest): Observable<Respuesta> {
    return this.http.patch<Respuesta>(this.url, saldoRequest);
  }
}
