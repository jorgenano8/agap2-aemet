package com.agap2.aemet.dto.prediccion;

import java.util.List;

import lombok.Data;

@Data
public class PrediccionDTO {

    private Double mediaTemperatura;
    private UnidadTemperatura unidadTemperatura;
    private List<ProbPrecipitacionDTO> probPrecipitacion;
}
