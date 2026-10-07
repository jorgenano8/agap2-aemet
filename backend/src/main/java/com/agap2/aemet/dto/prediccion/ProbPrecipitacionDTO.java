package com.agap2.aemet.dto.prediccion;

import lombok.Data;

@Data
public class ProbPrecipitacionDTO {
    private Integer probabilidad;
    private String periodo;

    public ProbPrecipitacionDTO(Integer probabilidad, String periodo) {
        this.probabilidad = probabilidad;
        this.periodo = periodo;
    }
}
