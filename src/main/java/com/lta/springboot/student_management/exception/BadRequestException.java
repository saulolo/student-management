package com.lta.springboot.student_management.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para solicitudes inválidas (400).
 */
public class BadRequestException extends ApiException{

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "BAD_REQUEST");
    }
}
