package com.lta.springboot.student_management.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para errores internos del servidor (500).
 */
public class InternalServerErrorException extends ApiException{

    public InternalServerErrorException(String message) {

        super(message, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR");
    }
}
