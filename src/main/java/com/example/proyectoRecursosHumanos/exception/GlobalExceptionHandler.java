package com.example.proyectoRecursosHumanos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- MANEJO DE EXCEPCIONES DE VALIDACIÓN (HTTP 400 Bad Request) ---
    /**
     * Intercepta la excepción lanzada cuando falla la validación @Valid o @Validated
     * en los DTOs/RequestBody. Devuelve una lista detallada de errores.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // 1. Recoger y formatear todos los errores de campo.
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = (error instanceof FieldError) ?
                            ((FieldError) error).getField() : "Objeto";
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .collect(Collectors.toList());

        // 2. Crear el cuerpo de respuesta base
        Map<String, Object> body = createBaseErrorBody(
                HttpStatus.BAD_REQUEST,
                "Validation Error",
                "La validación de la petición ha fallado. Verifique la lista de errores.",
                request.getRequestURI()
        );

        // 3. Agregar la lista detallada de errores
        body.put("validationErrors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // --- MANEJO DE EXCEPCIONES DE DOMINIO PERSONALIZADAS ---

    /**
     * Intercepta errores de recursos no encontrados (ej. un empleado por ID).
     * Mapea a HTTP 404 NOT FOUND.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

        return createErrorResponseEntity(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    /**
     * Intercepta errores de conflicto (ej. intento de crear un departamento con nombre duplicado).
     * Mapea a HTTP 409 CONFLICT.
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateResource(
            DuplicateResourceException ex, HttpServletRequest request) {

        return createErrorResponseEntity(
                HttpStatus.CONFLICT,
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(OperationConflictException.class)
    public ResponseEntity<Map<String, Object>> handleOperationConflict(
            OperationConflictException ex, HttpServletRequest request) {

        return createErrorResponseEntity(
                HttpStatus.CONFLICT, // 409
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    // --- MANEJO DE EXCEPCIONES GENÉRICAS (Incluyendo tu ejemplo original) ---

    /**
     * Intercepta errores de argumentos inválidos (ej. un valor que no cumple la lógica de negocio).
     * Mapea a HTTP 400 BAD REQUEST.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {

        return createErrorResponseEntity(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    // --- MÉTODOS DE AYUDA ---

    /**
     * Método interno para crear la estructura base del cuerpo del error JSON.
     */
    private Map<String, Object> createBaseErrorBody(
            HttpStatus status,
            String errorType,
            String message,
            String path) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", errorType);
        body.put("message", message);
        body.put("path", path);
        return body;
    }

    /**
     * Método interno para generar la ResponseEntity completa (cuerpo + código HTTP).
     */
    private ResponseEntity<Map<String, Object>> createErrorResponseEntity(
            HttpStatus status,
            String errorType,
            String message,
            String path) {

        Map<String, Object> body = createBaseErrorBody(status, errorType, message, path);
        return new ResponseEntity<>(body, status);
    }
}

