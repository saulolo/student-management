package com.lta.springboot.student_management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepción base para todas las excepciones personalizadas de la API.
 * Proporciona estructura común para el manejo de errores.
 */
@Getter
public abstract class ApiException extends RuntimeException{

    private final HttpStatus status;
    private final String errorCode;


    /**
     * Constructor con mensaje, status y código de error.
     *
     * @param message   mensaje descriptivo del error
     * @param status    código de estado HTTP asociado
     * @param errorCode código de error personalizado
     */
    protected ApiException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    /**
     * Constructor con mensaje, status, código de error y causa.
     *
     * @param message   mensaje descriptivo del error
     * @param status    código de estado HTTP asociado
     * @param errorCode código de error personalizado
     * @param cause     causa raíz de la excepción
     */
    protected ApiException(String message, HttpStatus status, String errorCode, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.errorCode = errorCode;
    }
}
