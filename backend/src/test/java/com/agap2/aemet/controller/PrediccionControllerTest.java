package com.agap2.aemet.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Method;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.agap2.aemet.dto.prediccion.UnidadTemperatura;
import com.agap2.aemet.service.PrediccionService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class PrediccionControllerTest {

    @Test
    void shouldRejectEmptyMunicipioCode() throws Exception {
        PrediccionController controller = new PrediccionController(null);
        Method method = PrediccionController.class.getMethod(
                "getPrediccion",
                String.class,
                UnidadTemperatura.class);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<PrediccionController>> violations = validator.forExecutables()
                .validateParameters(controller, method, new Object[] { "", UnidadTemperatura.G_CEL });

        assertFalse(violations.isEmpty());
    }
}
