package com.agap2.aemet.dto.municipio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor 
public class MunicipioDTO {
    private final String codigo;
    private final String nombre;
}
