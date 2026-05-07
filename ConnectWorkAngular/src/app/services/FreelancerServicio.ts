import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Freelancer } from '../models/freelancer';
import { Respuesta } from '../models/respuesta';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root',
})
export class FreelancerServicio {
  private url = `${environment.urlBaseApi}api/freelancer`;
  private http = inject(HttpClient);

  public registrarDatosInicialesFreelancer(freelancer: Freelancer): Observable<Respuesta> {
    return this.http.post<Respuesta>(this.url, freelancer);
  }
}
