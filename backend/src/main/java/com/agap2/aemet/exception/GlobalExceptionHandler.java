package com.agap2.aemet.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AemetResponseException.class)
    public ResponseEntity<ErrorResponse> handleAemetInvalidResponse(AemetResponseException exception) {
        logger.warn("Respuesta no utilizable de AEMET: {}", exception.getMessage());
        return createErrorResponse(HttpStatus.BAD_GATEWAY, "El servicio de AEMET devolvió datos no utilizables.");
    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ErrorResponse> handleAemetHttpError(RestClientResponseException exception) {
        int status = exception.getStatusCode().value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, exception.getStatusText(), "El servicio de AEMET ha respondido con un error."));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleAemetConnectionError(ResourceAccessException exception) {
        return createErrorResponse(HttpStatus.BAD_GATEWAY, "No se ha podido conectar con el servicio de AEMET.");
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorResponse> handleAemetClientError(RestClientException exception) {
        return createErrorResponse(HttpStatus.BAD_GATEWAY, "Se ha producido un error al consultar el servicio de AEMET.");
    }

    @ExceptionHandler({HandlerMethodValidationException.class, MethodArgumentNotValidException.class, MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleValidationError(Exception exception) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Los parámetros introducidos en la petición no son válidos.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedError(Exception exception) {
        logger.error("Error interno no controlado.", exception);
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Se ha producido un error interno.");
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status.value(), status.getReasonPhrase(), message));
    }
}
