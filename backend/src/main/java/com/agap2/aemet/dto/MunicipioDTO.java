package com.agap2.aemet.dto;

public class MunicipioDTO {
    private final String codigo;
    private final String nombre;

    public MunicipioDTO(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}
