package com.example.proyectoRecursosHumanos.exception;

// Al extender RuntimeException, es una excepción no comprobada (unchecked)
// y no obliga a los métodos a declararla con 'throws'.
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s : '%s'", resourceName, fieldName, fieldValue));
    }

    // Constructor que acepta mensaje y causa (si quisieras anidar otra excepción)
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}