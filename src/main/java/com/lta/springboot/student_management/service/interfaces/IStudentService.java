package com.lta.springboot.student_management.service.interfaces;

import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;

import java.util.List;

public interface IStudentService {

    /**
     * Obtiene la lista de todos los estudiantes registrados.
     *
     * @return lista de estudiantes representados mediante StudentResponseDTO.
     */
    List<StudentResponseDTO> findAllStudents();
}
