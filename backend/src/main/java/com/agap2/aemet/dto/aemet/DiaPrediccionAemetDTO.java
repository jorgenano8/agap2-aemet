package com.agap2.aemet.dto.aemet;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties (ignoreUnknown = true)
@Data
public class DiaPrediccionAemetDTO {
    private String fecha;
    private TemperaturaAemetDTO temperatura;
    private List<ProbPrecipitacionAemetDTO> probPrecipitacion;
}
