package com.agap2.aemet.dto.municipio;

import lombok.Data;

@Data
public class MunicipioDTO {
    private final String codigo;
    private final String nombre;

    public MunicipioDTO(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
}
