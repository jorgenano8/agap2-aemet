import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Prediccion, UnidadTemperatura } from '../models/prediccion.model';

@Injectable({
  providedIn: 'root',
})
export class PrediccionService {
  private readonly http = inject(HttpClient);

  obtenerPrediccion(codigoMunicipio: string, unidad: UnidadTemperatura = 'G_CEL' ): Observable<Prediccion> {
    const params = new HttpParams().set('unidad', unidad);
    return this.http.get<Prediccion>( `/api/prediccion/${encodeURIComponent(codigoMunicipio)}`, { params });
  }
}
