package com.agap2.aemet.controller;

import com.agap2.aemet.dto.municipio.MunicipioDTO;
import com.agap2.aemet.service.MunicipioService;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/municipio")
@Validated
public class MunicipioController {

    private final MunicipioService municipioService;

    public MunicipioController(MunicipioService municipioService) {
        this.municipioService = municipioService;
    }

    @GetMapping
    public List<MunicipioDTO> getMunicipiosByNombre(@RequestParam @NotBlank String nombre) {
        return municipioService.buscarMunicipios(nombre);
    }

}
