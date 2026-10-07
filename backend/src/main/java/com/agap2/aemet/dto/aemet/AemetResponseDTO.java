package com.agap2.aemet.dto.aemet;

import lombok.Data;

@Data
public class AemetResponseDTO {
    private String descripcion;
    private int estado;
    private String datos;
    private String metadatos;
}
