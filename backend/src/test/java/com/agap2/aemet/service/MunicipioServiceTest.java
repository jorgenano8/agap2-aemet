package com.agap2.aemet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agap2.aemet.client.AemetClient;
import com.agap2.aemet.dto.aemet.MunicipioAemetResponseDTO;
import com.agap2.aemet.dto.municipio.MunicipioDTO;

@ExtendWith(MockitoExtension.class)
class MunicipioServiceTest {

    @Mock
    private AemetClient aemetClient;

    @Test
    void buscaMunicipios() {
        MunicipioService service = new MunicipioService(aemetClient);
        when(aemetClient.getMunicipios()).thenReturn(List.of(municipio("id28079", "Madrid"), municipio("08019", "Barcelona")));

        List<MunicipioDTO> resultado = service.buscarMunicipios("mad");

        assertEquals(1, resultado.size());
        assertEquals("28079", resultado.get(0).getCodigo());
        assertEquals("Madrid", resultado.get(0).getNombre());
    }

    @Test
    void propagaFallo() {
        MunicipioService service = new MunicipioService(aemetClient);
        when(aemetClient.getMunicipios()).thenThrow(new RuntimeException("AEMET no disponible"));

        assertThrows(RuntimeException.class, () -> service.buscarMunicipios("mad"));
    }

    private MunicipioAemetResponseDTO municipio(String id, String nombre) {
        MunicipioAemetResponseDTO municipio = new MunicipioAemetResponseDTO();
        municipio.setId(id);
        municipio.setNombre(nombre);
        return municipio;
    }
}
