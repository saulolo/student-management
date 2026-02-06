package com.lta.springboot.student_management.service.interfaces;

import com.lta.springboot.student_management.domain.dto.request.StudentRequestDTO;
import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;

import java.util.List;

public interface IStudentService {

    /**
     * Obtiene la lista de todos los estudiantes registrados.
     *
     * @return lista de estudiantes representados mediante StudentResponseDTO.
     */
    List<StudentResponseDTO> findAllStudents();

    /**
     * Crea un nuevo estudiante en el sistema.
     *
     * @param studentRequestDTO datos del estudiante a crear
     * @return StudentResponseDTO con los datos del estudiante creado
     */
    StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO);
}
