package com.agap2.aemet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agap2.aemet.dto.prediccion.PrediccionDTO;
import com.agap2.aemet.dto.prediccion.UnidadTemperatura;
import com.agap2.aemet.service.PrediccionService;

@RestController
@RequestMapping("/api/prediccion")
public class PrediccionController {

    private final PrediccionService prediccionService;

    public PrediccionController(PrediccionService prediccionService) {
        this.prediccionService = prediccionService;
    }

    @GetMapping("/{codigo}")
    public PrediccionDTO getPrediccion(@PathVariable String codigo, @RequestParam(defaultValue = "G_CEL") UnidadTemperatura unidad) {
        return prediccionService.getPrediccion(codigo, unidad);
    }
}
