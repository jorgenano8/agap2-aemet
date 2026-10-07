package com.agap2.aemet.dto.aemet;

import java.util.List;

import lombok.Data;

@Data
public class DiaPrediccionAemetDTO {
    private String fecha;
    private TemperaturaAemetDTO temperatura;
    private List<ProbPrecipitacionAemetDTO> probPrecipitacion;
}
