package com.lta.springboot.student_management.domain.mapper;

import com.lta.springboot.student_management.domain.dto.request.StudentRequestDTO;
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


    // ========== ENTITY → RESPONSE DTO ==========

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


    // ========== REQUEST DTO → ENTITY ==========

    /**
     * Convierte un {@link StudentRequestDTO} a una entidad {@link Student}.
     * Usado para CREAR un nuevo estudiante.
     *
     * @param studentRequestDTO el DTO con los datos del estudiante.
     * @return entidad Student sin ID (será asignado por la BD).
     */
    public Student toEntity(StudentRequestDTO studentRequestDTO) {
        if (studentRequestDTO == null) {
            return null;
        }

        return Student.builder()
                .name(studentRequestDTO.getName())
                .lastName(studentRequestDTO.getLastName())
                .email(studentRequestDTO.getEmail())
                .build();
    }

    /**
     * Actualiza una entidad {@link Student} existente con datos de {@link StudentRequestDTO}.
     * Usado para EDITAR un estudiante.
     *
     * @param student           la entidad existente a actualizar.
     * @param studentRequestDTO el DTO con los nuevos datos.
     */
    public void updateEntityFromDTO(Student student, StudentRequestDTO studentRequestDTO) {
        if (student != null && studentRequestDTO != null) {
            student.setName(studentRequestDTO.getName());
            student.setLastName(studentRequestDTO.getLastName());
            student.setEmail(studentRequestDTO.getEmail());
        }
    }


}
