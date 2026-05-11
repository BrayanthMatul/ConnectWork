import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/operators';
import { FreelancerHabilidad } from '../models/freelancer-habilidad';

@Injectable({
  providedIn: 'root',
})
export class FreelancerHabilidadServicio {
  private url = `${environment.urlBaseApi}api/freelancer-habilidad`;
  private http = inject(HttpClient);

  public obtenerFreelancerHabilidades(): Observable<FreelancerHabilidad[]> {
    return this.http.get<FreelancerHabilidad[]>(this.url);
  }

  public obtenerHabilidadesPorFreelancer(idFreelancer: number): Observable<FreelancerHabilidad[]> {
    return this.obtenerFreelancerHabilidades().pipe(
      map((freelancerHabilidades) =>
        freelancerHabilidades.filter((fh) => fh.idFreelancer === idFreelancer),
      ),
    );
  }
}
