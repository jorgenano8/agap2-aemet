package com.agap2.aemet.dto.aemet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties (ignoreUnknown = true)
@Data
public class MunicipioAemetResponseDTO {
    private String id;
    private String nombre;
}
