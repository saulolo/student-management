package com.lta.springboot.student_management.exception;

import org.springframework.http.HttpStatus;

import static com.lta.springboot.student_management.util.Constants.BAD_REQUEST;

/**
 * Excepción para solicitudes inválidas (400).
 */
public class BadRequestException extends ApiException{

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, BAD_REQUEST);
    }
}
