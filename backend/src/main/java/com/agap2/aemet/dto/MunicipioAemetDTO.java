package com.agap2.aemet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MunicipioAemetDTO {
    private String latitud;
    private String idOld;
    private String url;
    private String latitudDec;
    private String altitud;
    private String capital;
    private String numHab;
    private String zonaComarcal;
    private String destacada;
    private String nombre;

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getIdOld() {
        return idOld;
    }

    public void setIdOld(String idOld) {
        this.idOld = idOld;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatitudDec() {
        return latitudDec;
    }

    public void setLatitudDec(String latitudDec) {
        this.latitudDec = latitudDec;
    }

    public String getAltitud() {
        return altitud;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getNumHab() {
        return numHab;
    }

    public void setNumHab(String numHab) {
        this.numHab = numHab;
    }

    public String getZonaComarcal() {
        return zonaComarcal;
    }

    public void setZonaComarcal(String zonaComarcal) {
        this.zonaComarcal = zonaComarcal;
    }

    public String getDestacada() {
        return destacada;
    }

    public void setDestacada(String destacada) {
        this.destacada = destacada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
