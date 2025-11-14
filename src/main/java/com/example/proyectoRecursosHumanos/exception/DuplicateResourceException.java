package com.example.proyectoRecursosHumanos.exception;

public class DuplicateResourceException extends RuntimeException {

    // Constructor que acepta solo el mensaje
    public DuplicateResourceException(String message) {
        super(message);
    }

    // Constructor que acepta mensaje y causa
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}