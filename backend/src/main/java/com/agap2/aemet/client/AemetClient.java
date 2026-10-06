package com.agap2.aemet.client;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.agap2.aemet.config.AemetProperties;
import com.agap2.aemet.dto.AemetResponseDTO;
import com.agap2.aemet.dto.MunicipioAemetDTO;

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
        this.externalRestClient = RestClient.builder().build();
    }

    public AemetResponseDTO getMunicipiosSource() {
        return restClient.get()
                .uri("/maestro/municipios")
                .header("api_key", properties.getApiKey())
                .retrieve()
                .body(AemetResponseDTO.class);
    }

    public List<MunicipioAemetDTO> getMunicipios() {
        AemetResponseDTO response = getMunicipiosSource();
        String municipiosJson = externalRestClient.get()
                .uri(response.getDatos())
                .retrieve()
                .body(String.class);

        try {
            return objectMapper.readValue(
                    municipiosJson,
                    new TypeReference<List<MunicipioAemetDTO>>() {
                    });
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("La respuesta de municipios de AEMET no contiene JSON correcto.", exception);
        }
    }

}
