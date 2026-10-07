package com.agap2.aemet.dto.aemet;

import java.util.List;

import lombok.Data;

@Data
public class PrediccionDTO {
    private List<DiaPrediccionAemetDTO> dia;
}
