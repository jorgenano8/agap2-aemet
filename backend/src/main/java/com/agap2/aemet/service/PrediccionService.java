package com.agap2.aemet.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agap2.aemet.client.AemetClient;
import com.agap2.aemet.dto.aemet.DiaPrediccionAemetDTO;
import com.agap2.aemet.dto.aemet.PrediccionAemetResponseDTO;
import com.agap2.aemet.dto.prediccion.PrediccionDTO;
import com.agap2.aemet.dto.prediccion.UnidadTemperatura;

@Service
public class PrediccionService {

    private final AemetClient aemetClient;

    public PrediccionService(AemetClient aemetClient) {
        this.aemetClient = aemetClient;
    }

    public PrediccionDTO getPrediccion(String codigo, UnidadTemperatura unidad) {
        List<PrediccionAemetResponseDTO> predicciones = aemetClient.getPrediccion(codigo);
        if (predicciones == null || predicciones.isEmpty()) {
            throw new IllegalStateException("No se encontraron predicciones para el código: " + codigo);
        }

        PrediccionAemetResponseDTO prediccionAemetDTO = predicciones.get(0);

        LocalDate manana = LocalDate.now().plusDays(1);

        DiaPrediccionAemetDTO diaPrediccion = prediccionAemetDTO.getPrediccion().getDia().stream()
                .filter(dia -> LocalDateTime.parse(dia.getFecha()).toLocalDate().equals(manana))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró la predicción para mañana."));

        return new PrediccionDTO();
    }

}
