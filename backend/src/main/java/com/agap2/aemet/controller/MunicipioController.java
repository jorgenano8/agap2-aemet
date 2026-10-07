package com.agap2.aemet.controller;

import com.agap2.aemet.dto.municipio.MunicipioDTO;
import com.agap2.aemet.service.MunicipioService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/municipio")
public class MunicipioController {

    private final MunicipioService municipioService;

    private MunicipioController(MunicipioService municipioService) {
        this.municipioService = municipioService;
    }

    @GetMapping
    public List<MunicipioDTO> getMunicipiosByNombre(@RequestParam String nombre) {
        return municipioService.buscarMunicipios(nombre);
    }

}
