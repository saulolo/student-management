package com.lta.springboot.student_management.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para respuestas de error estandarizadas.
 */
@Builder
public record ErrorResponseDTO(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        int status,
        String error,
        String errorCode,
        String message,
        String path,
        List<String> details) {

        /**
         * Constructor compacto que asigna timestamp actual si es null.
         */
        public ErrorResponseDTO {
                if (timestamp == null) {
                        timestamp = LocalDateTime.now();
                }
        }

        /**
         * Constructor de conveniencia sin timestamp (se asigna automáticamente).
         */
        public ErrorResponseDTO(int status, String error, String errorCode, String message, String path, List<String> details) {
                this(LocalDateTime.now(), status, error, errorCode, message, path, details);
        }

        /**
         * Constructor de conveniencia sin detalles ni timestamp.
         */
        public ErrorResponseDTO(int status, String error, String errorCode, String message, String path) {
                this(LocalDateTime.now(), status, error, errorCode, message, path, null);
        }
}
