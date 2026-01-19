package com.lta.springboot.student_management.service.impl;

import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;
import com.lta.springboot.student_management.domain.entity.Student;
import com.lta.springboot.student_management.domain.mapper.StudentMapper;
import com.lta.springboot.student_management.repository.StudentRepository;
import com.lta.springboot.student_management.service.interfaces.IStudentService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<StudentResponseDTO> findAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        return studentMapper.toStudentResponseList(studentList);
    }
}
