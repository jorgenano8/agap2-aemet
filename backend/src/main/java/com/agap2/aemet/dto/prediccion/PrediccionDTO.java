package com.agap2.aemet.dto.prediccion;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class PrediccionDTO {

    private Double mediaTemperatura;
    private UnidadTemperatura unidadTemperatura;
    private List<ProbPrecipitacionDTO> probPrecipitacion;
}
