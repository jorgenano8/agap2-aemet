package com.agap2.aemet.client;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.agap2.aemet.config.AemetProperties;
import com.agap2.aemet.dto.aemet.AemetResponseDTO;
import com.agap2.aemet.dto.aemet.MunicipioAemetResponseDTO;
import com.agap2.aemet.dto.aemet.PrediccionAemetResponseDTO;
import com.agap2.aemet.exception.AemetResponseException;

@Component
public class AemetClient {

    private final AemetProperties properties;
    private final RestClient restClient;
    private final RestClient externalRestClient;
    private final ObjectMapper objectMapper;

    public AemetClient(RestClient.Builder restClientBuilder, AemetProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.restClient = restClientBuilder
                .baseUrl(properties.getBaseUrl())
                .build();
        this.externalRestClient = restClientBuilder.build();
    }

    public AemetResponseDTO getMunicipiosSource() {
        return restClient.get()
                .uri("/maestro/municipios")
                .header("api_key", properties.getApiKey())
                .retrieve()
                .body(AemetResponseDTO.class);
    }

    public List<MunicipioAemetResponseDTO> getMunicipios() {
        AemetResponseDTO response = getMunicipiosSource();
        validateResponse(response);
        String municipiosJson = externalRestClient.get()
                .uri(response.getDatos())
                .retrieve()
                .body(String.class);

        try {
            return objectMapper.readValue(
                    municipiosJson,
                    new TypeReference<List<MunicipioAemetResponseDTO>>() {
                    });
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("La respuesta de municipios de AEMET no contiene JSON correcto.", exception);
        }
    }

    public AemetResponseDTO getPrediccionSource(String municipioId) {
        return restClient.get()
                .uri("/prediccion/especifica/municipio/diaria/" + municipioId)
                .header("api_key", properties.getApiKey())
                .retrieve()
                .body(AemetResponseDTO.class);
    }

    public List<PrediccionAemetResponseDTO> getPrediccion(String municipioId) {
        AemetResponseDTO response = getPrediccionSource(municipioId);
        validateResponse(response);
        String prediccionJson = externalRestClient.get()
                .uri(response.getDatos())
                .retrieve()
                .body(String.class);

        try {
            return objectMapper.readValue(
                    prediccionJson,
                    new TypeReference<List<PrediccionAemetResponseDTO>>() {
                    });
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("La respuesta de predicción de AEMET no contiene JSON correcto.", exception);
        }
    }

    private void validateResponse(AemetResponseDTO response) {
        if (response == null || response.getEstado() != 200 || response.getDatos() == null || response.getDatos().isBlank()) {
            throw new AemetResponseException("AEMET devolvió una respuesta sin datos utilizables.");
        }
    }

}
