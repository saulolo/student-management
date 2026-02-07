package com.lta.springboot.student_management.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO para respuestas exitosas estandarizadas.
 *
 * @param <T> tipo de dato contenido en la respuesta
 */
@Builder
public record SuccessResponseDTO<T>(
        boolean success,
        String message,
        T data,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp) {
}
