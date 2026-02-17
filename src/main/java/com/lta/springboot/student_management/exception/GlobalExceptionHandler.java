package com.lta.springboot.student_management.exception;

import com.lta.springboot.student_management.domain.dto.response.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Manejador global de excepciones para toda la aplicación.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Maneja excepciones personalizadas de tipo {@link ApiException}.
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleApiException(ApiException ex, HttpServletRequest request) {
        log.warn("Error controlado: {} - {}", ex.getErrorCode(), ex.getMessage());

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .success(false)
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    /**
     * Maneja errores no controlados (genéricos).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Error inesperado: ", ex);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .success(false)
                .message("Ha ocurrido un error inesperado en el servidor.")
                .errorCode("INTERNAL_SERVER_ERROR")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


}
