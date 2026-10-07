import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Municipio } from '../models/municipio.model';

@Injectable({
  providedIn: 'root',
})
export class MunicipioService {
  private readonly http = inject(HttpClient);

  buscarMunicipios(nombre: string): Observable<Municipio[]> {
    const params = new HttpParams().set('nombre', nombre);
    return this.http.get<Municipio[]>('/api/municipio', { params });
  }
}
