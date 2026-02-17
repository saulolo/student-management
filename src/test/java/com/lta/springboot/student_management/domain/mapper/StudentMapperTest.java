package com.lta.springboot.student_management.domain.mapper;

import com.lta.springboot.student_management.data.DataDummy;
import com.lta.springboot.student_management.domain.dto.request.StudentRequestDTO;
import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;
import com.lta.springboot.student_management.domain.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private Student student;
    private StudentRequestDTO studentRequestDTO;
    private StudentResponseDTO studentResponseDTO;
    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        studentMapper = new StudentMapper();
        student = DataDummy.firstStudent();
        studentRequestDTO = DataDummy.createDefaultStudentRequestDTO();
        studentResponseDTO = DataDummy.createDefaultStudentResponseDTO();
    }

    @Test
    @DisplayName("toStudentResponseDTO - Convierte correctamente un Student en StudentResponseDTO")
    void testToStudentResponseDTO() {
        // When
        StudentResponseDTO result = studentMapper.toStudentResponseDTO(student);

        // Then
        assertNotNull(result);
        assertEquals(student.getIdStudent(), result.idStudent());
        assertEquals(student.getName(), result.name());
        assertEquals(student.getLastname(), result.lastname());
        assertEquals(student.getEmail(), result.email());
    }

    @Test
    @DisplayName("toStudentResponseDTO - Maneja null correctamente")
    void testToStudentResponseDTO_Null() {
        assertNull(studentMapper.toStudentResponseDTO(null));
    }

    @Test
    @DisplayName("toStudentResponseList - Convierte lista a DTO correctamente")
    void testToStudentResponseList() {
        List<Student> students = DataDummy.createStudentList();

        List<StudentResponseDTO> result = studentMapper.toStudentResponseList(students);

        assertNotNull(result);
        assertEquals(students.size(), result.size());
        assertEquals(students.get(0).getName(), result.get(0).name());
    }

    @Test
    @DisplayName("toStudentResponseList - Maneja lista vacía o null correctamente")
    void testToStudentResponseList_EmptyOrNull() {
        assertTrue(studentMapper.toStudentResponseList(List.of()).isEmpty());
        assertTrue(studentMapper.toStudentResponseList(null).isEmpty());
    }

    @Test
    @DisplayName("toEntity - Convierte correctamente StudentRequestDTO a Student")
    void testToEntity() {
        Student result = studentMapper.toEntity(studentRequestDTO);

        assertNotNull(result);
        assertEquals(studentRequestDTO.getName(), result.getName());
        assertEquals(studentRequestDTO.getLastname(), result.getLastname());
        assertEquals(studentRequestDTO.getEmail(), result.getEmail());
        assertNull(result.getIdStudent());
    }

    @Test
    @DisplayName("toEntity - Maneja null correctamente")
    void testToEntity_Null() {
        assertNull(studentMapper.toEntity(null));
    }

    @Test
    @DisplayName("updateEntityFromDTO - Actualiza los campos de Student")
    void testUpdateEntityFromDTO() {
        Student studentToUpdate = DataDummy.firstStudent();
        StudentRequestDTO updateDTO = StudentRequestDTO.builder()
                .name("Actualizado")
                .lastname("Modificado")
                .email("nuevo@email.com")
                .build();

        studentMapper.updateEntityFromDTO(studentToUpdate, updateDTO);

        assertEquals("Actualizado", studentToUpdate.getName());
        assertEquals("Modificado", studentToUpdate.getLastname());
        assertEquals("nuevo@email.com", studentToUpdate.getEmail());
    }

    @Test
    @DisplayName("updateEntityFromDTO - No explota con nulls")
    void testUpdateEntityFromDTO_Nulls() {
        assertDoesNotThrow(() -> studentMapper.updateEntityFromDTO(null, studentRequestDTO));
        assertDoesNotThrow(() -> studentMapper.updateEntityFromDTO(student, null));
    }
}