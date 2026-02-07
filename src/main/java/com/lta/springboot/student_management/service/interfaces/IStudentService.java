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

    /**
     * Busca un estudiante por su ID.
     *
     * @param id identificador único del estudiante
     * @return StudentResponseDTO con los datos del estudiante encontrado
     */
    StudentResponseDTO findStudentById(Long id);

    /**
     * Actualiza los datos de un estudiante existente.
     *
     * @param id                identificador del estudiante a actualizar
     * @param studentRequestDTO datos actualizados del estudiante
     * @return StudentResponseDTO con los datos del estudiante actualizado
     */
    StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO);

    /**
     * Elimina un estudiante del sistema por su ID.
     *
     * @param id identificador del estudiante a eliminar
     */
    void deleteStudentById(Long id);

}
