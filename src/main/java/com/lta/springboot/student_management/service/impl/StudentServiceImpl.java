package com.lta.springboot.student_management.service.impl;

import com.lta.springboot.student_management.domain.dto.request.StudentRequestDTO;
import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;
import com.lta.springboot.student_management.domain.entity.Student;
import com.lta.springboot.student_management.domain.mapper.StudentMapper;
import com.lta.springboot.student_management.exception.ResourceNotFoundException;
import com.lta.springboot.student_management.repository.StudentRepository;
import com.lta.springboot.student_management.service.interfaces.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements IStudentService {


    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }


    /**
     * Recupera todos los estudiantes existentes en la base de datos,
     * transforma cada entidad {@link Student} a un {@link StudentResponseDTO}
     * y retorna la lista resultante.
     *
     * @return lista de estudiantes como DTOs.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> findAllStudents() {
        log.info("Obteniendo todos los estudiantes.");
        List<Student> studentList = studentRepository.findAll();
        return studentMapper.toStudentResponseList(studentList);
    }

    /**
     * Crea un nuevo estudiante en el sistema.
     *
     * @param studentRequestDTO datos del estudiante a crear
     * @return StudentResponseDTO con los datos del estudiante creado
     */
    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        log.info("Creando un nuevo estudiante con email: {}", studentRequestDTO.getEmail());
        // Convertir DTO a entidad
        Student student = studentMapper.toEntity(studentRequestDTO);

        // Guardar en la BD
        Student savedStudent = studentRepository.save(student);
        log.info("Estudiante creado exitosamente con ID: {}", savedStudent.getIdStudent());

        // Convertir entidad guardada a DTO de respuesta
        return studentMapper.toStudentResponseDTO(savedStudent);
    }

    /**
     * Busca un estudiante por su ID.
     *
     * @param id identificador único del estudiante
     * @return StudentResponseDTO con los datos del estudiante encontrado
     * @throws ResourceNotFoundException si no se encuentra el estudiante con el ID especificado
     */
    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO findStudentById(Long id) {
        log.info("Buscando estudiante con ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante con ID " + id + " no encontrado"));
        log.info("Estudiante encontrado: {} {}", student.getName(), student.getLastname());
        return studentMapper.toStudentResponseDTO(student);
    }

    /**
     * Actualiza los datos de un estudiante existente.
     *
     * @param id                identificador del estudiante a actualizar
     * @param studentRequestDTO datos actualizados del estudiante
     * @return StudentResponseDTO con los datos del estudiante actualizado
     * @throws ResourceNotFoundException si no se encuentra el estudiante con el ID especificado
     */
    @Override
    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
        log.info("Actualizando estudiante con ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante con ID " + id + " no encontrado"));

        studentMapper.updateEntityFromDTO(student, studentRequestDTO);

        Student updatedStudent = studentRepository.save(student);
        log.info("Estudiante actualizado exitosamente: {} {} {}", student.getIdStudent(), student.getName(),
                student.getLastname());

        return studentMapper.toStudentResponseDTO(updatedStudent);
    }

    /**
     * Elimina un estudiante del sistema por su ID.
     *
     * @param id identificador del estudiante a eliminar
     * @throws ResourceNotFoundException si no se encuentra el estudiante con el ID especificado
     */
    @Override
    @Transactional
    public void deleteStudentById(Long id) {
        log.info("Eliminando estudiante con ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante con ID " + id + " no encontrado"));

        studentRepository.deleteById(id);
        log.info("Estudiante eliminado exitosamente - ID: {}, Nombre: {} {}",
                id, student.getName(), student.getLastname());
    }
}
