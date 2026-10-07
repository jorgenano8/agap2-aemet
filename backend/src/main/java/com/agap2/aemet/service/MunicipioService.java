package com.agap2.aemet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agap2.aemet.client.AemetClient;
import com.agap2.aemet.dto.aemet.MunicipioAemetResponseDTO;
import com.agap2.aemet.dto.municipio.MunicipioDTO;

@Service
public class MunicipioService {

    private final AemetClient aemetClient;

    public MunicipioService(AemetClient aemetClient) {
        this.aemetClient = aemetClient;
    }

    public List<MunicipioDTO> buscarMunicipios(String nombre) {
        List<MunicipioAemetResponseDTO> municipiosAemet = aemetClient.getMunicipios();

        return municipiosAemet.stream()
                .filter(municipio -> municipio.getNombre().toLowerCase().startsWith(nombre.toLowerCase()))
                .map(municipio -> new MunicipioDTO(eliminarPrefijoId(municipio.getId()), municipio.getNombre()))
                .toList();
    }

    private String eliminarPrefijoId(String id) {
        if (id == null) {
            return null;
        }

        if (id.startsWith("id")) {
            return id.substring(2);
        }

        return id;
    }
}
