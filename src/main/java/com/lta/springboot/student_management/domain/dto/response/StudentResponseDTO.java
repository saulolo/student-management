package com.lta.springboot.student_management.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 *  DTO de respuesta para exponer información de estudiantes
 *  al cliente o capa de presentación. Inmutable y serializable.
 */
@Builder
@JsonPropertyOrder({"idStudent", "name", "lastName", "email", "createdAt", "updateAt"})
public record StudentResponseDTO(
        Long idStudent,
        String name,
        String lastName,
        String email,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updateAt

) {
}
