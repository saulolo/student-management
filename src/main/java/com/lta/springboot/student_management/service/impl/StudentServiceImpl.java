package com.lta.springboot.student_management.service.impl;

import com.lta.springboot.student_management.domain.dto.request.StudentRequestDTO;
import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;
import com.lta.springboot.student_management.domain.entity.Student;
import com.lta.springboot.student_management.domain.mapper.StudentMapper;
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
}
