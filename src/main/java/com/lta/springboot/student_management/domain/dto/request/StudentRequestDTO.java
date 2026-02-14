package com.lta.springboot.student_management.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DTO para recibir datos de creación o actualización de estudiantes
 * desde formularios.
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentRequestDTO {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(
            min = 2,  // ← Prevenir nombres de 1 letra
            max = 50,
            message = "El nombre debe tener entre 2 y 50 caracteres."
    )
    String name;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(
            min = 2,
            max = 50,
            message = "El apellido debe tener entre 2 y 50 caracteres."
    )
    String lastname;

    @NotBlank(message = "El email es obligatorio.")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres.")
    @Email(
            message = "El formato del email no es válido.",
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )
    String email;
}
