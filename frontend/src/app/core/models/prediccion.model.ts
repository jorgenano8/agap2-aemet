export type UnidadTemperatura = 'G_CEL' | 'G_FAH';

export interface ProbabilidadPrecipitacion {
  probabilidad: number;
  periodo: string;
}

export interface Prediccion {
  mediaTemperatura: number;
  unidadTemperatura: UnidadTemperatura;
  probPrecipitacion: ProbabilidadPrecipitacion[];
}
