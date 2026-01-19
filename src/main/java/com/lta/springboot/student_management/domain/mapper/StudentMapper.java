package com.lta.springboot.student_management.domain.mapper;

import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;
import com.lta.springboot.student_management.domain.entity.Student;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Componente encargado de convertir la entidad {@link Student}
 * a suo DTO {@link StudentResponseDTO} asociado.
 */
@Component
public class StudentMapper {

    /**
     * Convierte un objeto {@link Student} a {@link StudentResponseDTO}.
     *
     * @param student la entidad Student a convertir.
     * @return StudentResponseDTO resultante o null si la entrada es null.
     */
    public StudentResponseDTO toStudentResponseDTO(Student student) {
        if (student == null) {
            return null;
        }
        return StudentResponseDTO.builder()
                .idStudent(student.getIdStudent())
                .name(student.getName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .createdAt(student.getCreatedAt())
                .updateAt(student.getUpdateAt())
                .build();
    }


    /**
     * Convierte una lista de entidades {@link Student} a una lista de {@link StudentResponseDTO}.
     *
     * @param studentList lista de entidades Student a convertir.
     * @return lista de DTOs (puede ser vacía, nunca null).
     */
    public List<StudentResponseDTO> toStudentResponseList(List<Student> studentList) {
        if (studentList == null || studentList.isEmpty()) {
            return Collections.emptyList();
        }
        return studentList.stream()
                .map(this::toStudentResponseDTO)
                .toList();
    }
}
