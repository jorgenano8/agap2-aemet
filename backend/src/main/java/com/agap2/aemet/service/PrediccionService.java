package com.agap2.aemet.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.agap2.aemet.client.AemetClient;
import com.agap2.aemet.dto.aemet.DiaPrediccionAemetDTO;
import com.agap2.aemet.dto.aemet.PrediccionAemetResponseDTO;
import com.agap2.aemet.dto.aemet.TemperaturaAemetDTO;
import com.agap2.aemet.dto.prediccion.PrediccionDTO;
import com.agap2.aemet.dto.prediccion.ProbPrecipitacionDTO;
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

        // Obtener la predicción de mañana
        LocalDate manana = LocalDate.now().plusDays(1);
        DiaPrediccionAemetDTO diaPrediccion = prediccionAemetDTO.getPrediccion().getDia().stream()
                .filter(dia -> LocalDateTime.parse(dia.getFecha()).toLocalDate().equals(manana))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró la predicción para mañana."));

        // Calcular media de temperatura y convertir a la unidad deseada
        TemperaturaAemetDTO temperatura = diaPrediccion.getTemperatura();
        double temperaturaMedia = (temperatura.getMaxima() + temperatura.getMinima()) / 2.0;
        if (unidad == UnidadTemperatura.G_FAH) {
            temperaturaMedia = (temperaturaMedia * 9 / 5) + 32;
        }

        // Obtener la probabilidad de precipitación
        Set<String> periodosPermitidos = Set.of("00-06", "06-12", "12-18", "18-24");
        List<ProbPrecipitacionDTO> probPrecipitacion = diaPrediccion.getProbPrecipitacion().stream()
                .filter(prob -> periodosPermitidos.contains(prob.getPeriodo()))
                .map(prob -> new ProbPrecipitacionDTO(prob.getValue(), prob.getPeriodo()))
                .toList();

        return new PrediccionDTO(temperaturaMedia, unidad, probPrecipitacion);
    }

}
