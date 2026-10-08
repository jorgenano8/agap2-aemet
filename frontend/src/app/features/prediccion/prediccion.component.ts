import { AsyncPipe, DecimalPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { catchError, debounceTime, distinctUntilChanged, EMPTY, filter, map, Observable, of, startWith, switchMap } from 'rxjs';
import { Municipio } from '../../core/models/municipio.model';
import { Prediccion, UnidadTemperatura } from '../../core/models/prediccion.model';
import { MunicipioService } from '../../core/services/municipio.service';
import { PrediccionService } from '../../core/services/prediccion.service';

@Component({
  selector: 'app-prediccion',
  imports: [AsyncPipe, DecimalPipe, ReactiveFormsModule],
  templateUrl: './prediccion.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PrediccionComponent {
  private readonly municipioService = inject(MunicipioService);
  private readonly prediccionService = inject(PrediccionService);

  readonly formulario = new FormGroup({
    municipio: new FormControl('', { nonNullable: true }),
    unidad: new FormControl<UnidadTemperatura | ''>('', { nonNullable: true }),
  });
  readonly opcionesUnidad = [
    { valor: '', etiqueta: 'Unidad' },
    { valor: 'G_CEL', etiqueta: '°C' },
    { valor: 'G_FAH', etiqueta: '°F' },
  ] as const;

  readonly municipioSeleccionado = signal<Municipio | null>(null);
  readonly prediccion = signal<Prediccion | null>(null);
  readonly error = signal<string | null>(null);
  readonly fechaManana = this.formatearFechaManana();

  readonly municipios$ = this.formulario.controls.municipio.valueChanges.pipe(
    map((nombre) => nombre.trim()),
    filter((nombre) => nombre !== this.municipioSeleccionado()?.nombre),
    debounceTime(500),
    distinctUntilChanged(),
    switchMap((nombre) => nombre.length < 2 ? of([])
      : this.municipioService.buscarMunicipios(nombre).pipe(
        catchError(() => {
          this.error.set('No se pudieron cargar los municipios. Por favor, inténtalo de nuevo más tarde.');
          return of([]);
        }),
      ),
    ),
  );

  constructor() {
    this.formulario.valueChanges
      .pipe(
        takeUntilDestroyed(),
        switchMap(({ municipio, unidad }) =>
          this.actualizarPrediccion(municipio ?? '', unidad ?? ''),
        ),
      )
      .subscribe((prediccion) => this.prediccion.set(prediccion));
  }

  seleccionarMunicipio(municipio: Municipio): void {
    this.municipioSeleccionado.set(municipio);
    this.prediccion.set(null);
    this.formulario.controls.municipio.setValue(municipio.nombre);
  }

  private actualizarPrediccion(nombreMunicipio: string, unidad: UnidadTemperatura | ''): Observable<Prediccion | null> {
    const municipio = this.municipioSeleccionado();

    if (!municipio || nombreMunicipio !== municipio.nombre) {
      return EMPTY;
    }

    this.error.set(null);

    return this.prediccionService.obtenerPrediccion(municipio.codigo, unidad || 'G_CEL').pipe(
      catchError(() => {
        this.error.set('No se pudo cargar la predicción de ' + municipio.nombre + '. Por favor, inténtalo de nuevo más tarde.');
        return of(null);
      }),
    );
  }

  private formatearFechaManana(): string {
    const fechaManana = new Date();
    fechaManana.setDate(fechaManana.getDate() + 1);

    const dia = String(fechaManana.getDate()).padStart(2, '0');
    const mes = String(fechaManana.getMonth() + 1).padStart(2, '0');
    const año = fechaManana.getFullYear();

    return `${dia}/${mes}/${año}`;
  }
}
