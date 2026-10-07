package com.agap2.aemet.dto.prediccion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor 
public class ProbPrecipitacionDTO {
    private Integer probabilidad;
    private String periodo;
}
