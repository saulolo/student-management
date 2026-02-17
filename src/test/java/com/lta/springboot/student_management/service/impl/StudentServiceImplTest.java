package com.lta.springboot.student_management.service.impl;

import com.lta.springboot.student_management.data.DataDummy;
import com.lta.springboot.student_management.domain.dto.request.StudentRequestDTO;
import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;
import com.lta.springboot.student_management.domain.entity.Student;
import com.lta.springboot.student_management.domain.mapper.StudentMapper;
import com.lta.springboot.student_management.exception.ResourceNotFoundException;
import com.lta.springboot.student_management.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para {@link StudentServiceImpl}
 * <p>
 * Se utiliza Mockito para simular las dependencias (StudentRepository y StudentMapper)
 * y JUnit 5 para las aserciones y estructura de las pruebas.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("StudentServiceImpl - Unit Tests")
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentRequestDTO studentRequestDTO;
    private StudentResponseDTO studentResponseDTO;

    @BeforeEach
    void setUp() {
        student = DataDummy.firstStudent();
        studentRequestDTO = DataDummy.createDefaultStudentRequestDTO();
        studentResponseDTO = DataDummy.createDefaultStudentResponseDTO();
    }

    @Test
    @DisplayName("findAllStudents - Debe retornar lista de estudiantes cuando existen registros")
    void testFindAllStudents_ShouldReturnListOfStudents_WhenStudentsExist() {
        //Given
        List<Student> students = DataDummy.createStudentList();
        List<StudentResponseDTO> expectedStudentResponseDTOList = DataDummy.createStudentResponseDTOList();

        when(studentRepository.findAll()).thenReturn(students);
        when(studentMapper.toStudentResponseList(students)).thenReturn(expectedStudentResponseDTOList);

        //When
        List<StudentResponseDTO> result = studentService.findAllStudents();

        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Saul", result.getFirst().name());
        assertEquals("Echeverri", result.getFirst().lastname());

        verify(studentRepository, times(1)).findAll();
        verify(studentMapper, times(1)).toStudentResponseList(students);

    }

    @Test
    @DisplayName("createStudent - Crea un estudiante cuando los registros son válidos")
    void testCreateStudent_ShouldReturnStudentResponseDTO_WhenValidData() {
        // Given

        when(studentMapper.toEntity(studentRequestDTO)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.toStudentResponseDTO(student)).thenReturn(studentResponseDTO);

        // When
        StudentResponseDTO result = studentService.createStudent(studentRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(studentResponseDTO, result);
        verify(studentMapper).toEntity(studentRequestDTO);
        verify(studentRepository).save(student);
        verify(studentMapper).toStudentResponseDTO(student);
    }

    @Test
    @DisplayName("findStudentById -  Debe retornar StudentResponseDTO cuando existe el estudiante")
    void testFindStudentById_ShouldReturnStudentResponseDTO_WhenStudentExists() {
        // Given
        Long idStudent = DataDummy.DEFAULT_ID;
        StudentResponseDTO expectedResponse = studentResponseDTO;

        when(studentRepository.findById(idStudent)).thenReturn(Optional.of(student));
        when(studentMapper.toStudentResponseDTO(student)).thenReturn(expectedResponse);

        // When
        StudentResponseDTO result = studentService.findStudentById(idStudent);

        //Then
        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(studentRepository).findById(idStudent);
        verify(studentMapper).toStudentResponseDTO(student);
    }

    @Test
    @DisplayName("findStudentById - Debe lanzar ResourceNotFoundException cuando el estudiante no existe")
    void testFindStudentById_ShouldThrowException_WhenStudentDoesNotExist() {
        // GIVEN
        Long id = 99L;
        when(studentRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> studentService.findStudentById(id));

        assertEquals("Estudiante con ID 99 no encontrado", ex.getMessage());
        verify(studentRepository).findById(id);
        verify(studentMapper, never()).toStudentResponseDTO(any());
    }

    @Test
    @DisplayName("updateStudent - Debe actualizar y retornar StudentResponseDTO cuando existe el estudiante")
    void testUpdateStudent_ShouldUpdateAndReturnStudentResponseDTO_WhenStudentExists() {
        // GIVEN
        Long id = DataDummy.DEFAULT_ID;
        StudentRequestDTO updateRequest = StudentRequestDTO.builder()
                .name("NuevoNombre")
                .lastname("NuevoApellido")
                .email("nuevocorreo@test.com")
                .build();

        Student existingStudent = DataDummy.firstStudent();
        Student updatedStudent = Student.builder()
                .idStudent(id)
                .name(updateRequest.getName())
                .lastname(updateRequest.getLastname())
                .email(updateRequest.getEmail())
                .createdAt(existingStudent.getCreatedAt())
                .updatedAt(existingStudent.getUpdatedAt())
                .build();

        StudentResponseDTO expectedResponse = DataDummy.createCustomStudentResponseDTO(
                id, "NuevoNombre", "NuevoApellido", "nuevocorreo@test.com",
                existingStudent.getCreatedAt(), existingStudent.getUpdatedAt());

        when(studentRepository.findById(id)).thenReturn(java.util.Optional.of(existingStudent));
        doNothing().when(studentMapper).updateEntityFromDTO(existingStudent, updateRequest);
        when(studentRepository.save(existingStudent)).thenReturn(updatedStudent);
        when(studentMapper.toStudentResponseDTO(updatedStudent)).thenReturn(expectedResponse);

        // WHEN
        StudentResponseDTO result = studentService.updateStudent(id, updateRequest);

        // THEN
        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(studentRepository).findById(id);
        verify(studentMapper).updateEntityFromDTO(existingStudent, updateRequest);
        verify(studentRepository).save(existingStudent);
        verify(studentMapper).toStudentResponseDTO(updatedStudent);
    }

    @Test
    @DisplayName("updateStudent - Debe lanzar ResourceNotFoundException cuando el estudiante no existe")
    void testUpdateStudent_ShouldThrowException_WhenStudentDoesNotExist() {
        // GIVEN
        Long id = 99L;
        StudentRequestDTO updateRequest = DataDummy.createDefaultStudentRequestDTO();

        when(studentRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> studentService.updateStudent(id, updateRequest));

        assertEquals("Estudiante con ID 99 no encontrado", ex.getMessage());
        verify(studentRepository).findById(id);
        verify(studentMapper, never()).updateEntityFromDTO(any(), any());
        verify(studentRepository, never()).save(any());
    }

    @Test
    @DisplayName("deleteStudentById - Debe eliminar estudiante cuando existe el estudiante")
    void testDeleteStudentById_ShouldDeleteStudent_WhenStudentExists() {
        // GIVEN
        Long id = DataDummy.DEFAULT_ID;
        Student student = DataDummy.firstStudent();
        when(studentRepository.findById(id)).thenReturn(java.util.Optional.of(student));
        doNothing().when(studentRepository).deleteById(id);

        // WHEN & THEN
        assertDoesNotThrow(() -> studentService.deleteStudentById(id));
        verify(studentRepository).findById(id);
        verify(studentRepository).deleteById(id);
    }

    @Test
    @DisplayName("deleteStudentById - Debe lanzar ResourceNotFoundException cuando el estudiante no existe")
    void testDeleteStudentById_ShouldThrowException_WhenStudentDoesNotExist() {
        // GIVEN
        Long id = 99L;
        when(studentRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> studentService.deleteStudentById(id));

        assertEquals("Estudiante con ID 99 no encontrado", ex.getMessage());
        verify(studentRepository).findById(id);
        verify(studentRepository, never()).deleteById(any());
    }

}