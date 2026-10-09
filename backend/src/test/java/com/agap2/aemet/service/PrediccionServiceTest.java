package com.agap2.aemet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agap2.aemet.client.AemetClient;
import com.agap2.aemet.dto.aemet.DiaPrediccionAemetDTO;
import com.agap2.aemet.dto.aemet.PrediccionAemetDTO;
import com.agap2.aemet.dto.aemet.PrediccionAemetResponseDTO;
import com.agap2.aemet.dto.aemet.ProbPrecipitacionAemetDTO;
import com.agap2.aemet.dto.aemet.TemperaturaAemetDTO;
import com.agap2.aemet.dto.prediccion.PrediccionDTO;
import com.agap2.aemet.dto.prediccion.UnidadTemperatura;

@ExtendWith(MockitoExtension.class)
class PrediccionServiceTest {

    @Mock
    private AemetClient aemetClient;

    @Test
    void calculaPrediccion() {
        PrediccionService service = new PrediccionService(aemetClient);
        when(aemetClient.getPrediccion("28079")).thenReturn(List.of(obtienePrediccion()));

        PrediccionDTO resultado = service.getPrediccion("28079", UnidadTemperatura.G_CEL);

        assertEquals(15.0, resultado.getMediaTemperatura());
        assertEquals(UnidadTemperatura.G_CEL, resultado.getUnidadTemperatura());
        assertEquals(1, resultado.getProbPrecipitacion().size());
        assertEquals("00-06", resultado.getProbPrecipitacion().get(0).getPeriodo());
        assertEquals(30, resultado.getProbPrecipitacion().get(0).getProbabilidad());
        verify(aemetClient).getPrediccion("28079");
    }

    @Test
    void conviertirFahrenheit() {
        PrediccionService service = new PrediccionService(aemetClient);
        when(aemetClient.getPrediccion("28079")).thenReturn(List.of(obtienePrediccion()));

        PrediccionDTO resultado = service.getPrediccion("28079", UnidadTemperatura.G_FAH);

        assertEquals(59.0, resultado.getMediaTemperatura());
        assertEquals(UnidadTemperatura.G_FAH, resultado.getUnidadTemperatura());
    }

    @Test
    void fallaPrediccion() {
        PrediccionService service = new PrediccionService(aemetClient);
        when(aemetClient.getPrediccion("28079")).thenReturn(List.of());

        assertThrows(IllegalStateException.class,
                () -> service.getPrediccion("28079", UnidadTemperatura.G_CEL));
    }

    @Test
    void propagaFallo() {
        PrediccionService service = new PrediccionService(aemetClient);
        when(aemetClient.getPrediccion("28079")).thenThrow(new RuntimeException("AEMET no disponible"));

        RuntimeException excepcion = assertThrows(RuntimeException.class,
                () -> service.getPrediccion("28079", UnidadTemperatura.G_CEL));

        assertEquals("AEMET no disponible", excepcion.getMessage());
    }

    private PrediccionAemetResponseDTO obtienePrediccion() {
        TemperaturaAemetDTO temperatura = new TemperaturaAemetDTO();
        temperatura.setMinima(10);
        temperatura.setMaxima(20);

        ProbPrecipitacionAemetDTO probabilidadPermitida = new ProbPrecipitacionAemetDTO();
        probabilidadPermitida.setPeriodo("00-06");
        probabilidadPermitida.setValue(30);

        ProbPrecipitacionAemetDTO probabilidadNoPermitida = new ProbPrecipitacionAemetDTO();
        probabilidadNoPermitida.setPeriodo("06-24");
        probabilidadNoPermitida.setValue(90);

        DiaPrediccionAemetDTO dia = new DiaPrediccionAemetDTO();
        dia.setFecha(LocalDate.now().plusDays(1).atStartOfDay().toString());
        dia.setTemperatura(temperatura);
        dia.setProbPrecipitacion(List.of(probabilidadPermitida, probabilidadNoPermitida));

        PrediccionAemetDTO prediccion = new PrediccionAemetDTO();
        prediccion.setDia(List.of(dia));

        PrediccionAemetResponseDTO respuesta = new PrediccionAemetResponseDTO();
        respuesta.setPrediccion(prediccion);
        return respuesta;
    }
}
