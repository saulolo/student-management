package com.lta.springboot.student_management.exception;

import org.springframework.http.HttpStatus;

import static com.lta.springboot.student_management.util.Constants.INTERNAL_SERVER_ERROR;

/**
 * Excepción para errores internos del servidor (500).
 */
public class InternalServerErrorException extends ApiException{

    public InternalServerErrorException(String message) {

        super(message, HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
    }
}
