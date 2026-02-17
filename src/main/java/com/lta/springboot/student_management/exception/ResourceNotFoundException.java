package com.lta.springboot.student_management.exception;

import org.springframework.http.HttpStatus;

import static com.lta.springboot.student_management.util.Constants.RESOURCE_NOT_FOUND;

/**
 * Excepción lanzada cuando no se encuentra un recurso solicitado.
 * Mapea a HTTP 404 NOT FOUND.
 */
public class ResourceNotFoundException extends ApiException{


    /**
     * Constructor con mensaje personalizado.
     *
     * @param message mensaje descriptivo del recurso no encontrado
     */
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND);
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message mensaje descriptivo del error
     * @param cause   causa raíz de la excepción
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND, cause);
    }
}
