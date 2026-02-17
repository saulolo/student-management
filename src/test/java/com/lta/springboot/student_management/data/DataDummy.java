package com.lta.springboot.student_management.data;

import com.lta.springboot.student_management.domain.dto.request.StudentRequestDTO;
import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;
import com.lta.springboot.student_management.domain.entity.Student;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class DataDummy {

    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_NAME = "Saul";
    public static final String DEFAULT_LASTNAME = "Echeverri";
    public static final String DEFAULT_EMAIL = "echeverri@test.com";
    public static final LocalDateTime DEFAULT_CREATED_AT = LocalDateTime.now();
    public static final LocalDateTime DEFAULT_UPDATED_AT = LocalDateTime.now();

    public static final Long SECOND_ID = 2L;
    public static final String SECOND_NAME = "Felipe";
    public static final String SECOND_LASTNAME = "Vasquez";
    public static final String SECOND_EMAIL = "vasquez@test.com";
    public static final LocalDateTime SECOND_CREATED_AT = LocalDateTime.now();
    public static final LocalDateTime SECOND_UPDATED_AT = LocalDateTime.now();

    public static final Long THIRD_ID = 3L;
    public static final String THIRD_NAME = "Alejandra";
    public static final String THIRD_LASTNAME = "Arenas";
    public static final String THIRD_EMAIL = "arenas@test.com";
    public static final LocalDateTime THIRD_CREATED_AT = LocalDateTime.now();
    public static final LocalDateTime THIRD_UPDATED_AT = LocalDateTime.now();


    // ==========================================
    // BUILDERS DE ENTIDADES (Student)
    // ==========================================

    /**
     * Crea un Student con valores por defecto.
     *
     * @return Student con sus respectivos datos
     */
    public static Student firstStudent() {
        log.info("Obteniendo Estudiante 1.");
        return Student.builder()
                .idStudent(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .lastname(DEFAULT_LASTNAME)
                .email(DEFAULT_EMAIL)
                .createdAt(DEFAULT_CREATED_AT)
                .updatedAt(DEFAULT_UPDATED_AT)
                .build();
    }

    /**
     * Crea el segundo estudiante predefinido.
     *
     * @return Student 2 con sus respectivos datos"
     */
    public static Student secondStudent() {
        log.info("Obteniendo Estudiante 2.");
        return Student.builder()
                .idStudent(SECOND_ID)
                .name(SECOND_NAME)
                .lastname(SECOND_LASTNAME)
                .email(SECOND_EMAIL)
                .createdAt(SECOND_CREATED_AT)
                .updatedAt(SECOND_UPDATED_AT)
                .build();
    }

    /**
     * Crea el tercer estudiante predefinido.
     *
     * @return Student 3 con sus respectivos datos
     */
    public static Student thirdStudent() {
        log.info("Obteniendo Estudiante 3.");
        return Student.builder()
                .idStudent(THIRD_ID)
                .name(THIRD_NAME)
                .lastname(THIRD_LASTNAME)
                .email(THIRD_EMAIL)
                .createdAt(THIRD_CREATED_AT)
                .updatedAt(THIRD_UPDATED_AT)
                .build();
    }

    // ==========================================
    // BUILDERS DE REQUEST DTOs
    // ==========================================

    /**
     * Crea un StudentRequestDTO con valores por defecto.
     *
     * @return StudentRequestDTO con datos de prueba
     */
    public static StudentRequestDTO createDefaultStudentRequestDTO() {
        log.info("Solicitando datos del estudiante.");
        return StudentRequestDTO.builder()
                .name(DEFAULT_NAME)
                .lastname(DEFAULT_LASTNAME)
                .email(DEFAULT_EMAIL)
                .build();
    }


    // ==========================================
    // BUILDERS DE RESPONSE DTOs
    // ==========================================
    /**
     * Crea un StudentResponseDTO con valores por defecto.
     *
     * @return StudentResponseDTO con datos de prueba
     */
    public static StudentResponseDTO createDefaultStudentResponseDTO() {
        return StudentResponseDTO.builder()
                .idStudent(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .lastname(DEFAULT_LASTNAME)
                .email(DEFAULT_EMAIL)
                .createdAt(DEFAULT_CREATED_AT)
                .updatedAt(DEFAULT_UPDATED_AT)
                .build();
    }

    /**
     * Crea un StudentResponseDTO personalizado.
     *
     * @param id ID
     * @param name Nombre
     * @param lastname Apellido
     * @param email Email
     * @param createdAt Email
     * @param updatedAt Email
     * @return StudentResponseDTO personalizado
     */
    public static StudentResponseDTO createCustomStudentResponseDTO(Long id, String name, String lastname, String email,
                                                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        return StudentResponseDTO.builder()
                .idStudent(id)
                .name(name)
                .lastname(lastname)
                .email(email)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }


    // ==========================================
    // BUILDERS DE LISTAS
    // ==========================================

    /**
     * Crea una lista de 3 estudiantes para tests de listado.
     *
     * @return Lista con 3 Students
     */
    public static List<Student> createStudentList() {
        return List.of(
                firstStudent(),
                secondStudent(),
                thirdStudent()
        );
    }

    /**
     * Crea una lista de StudentResponseDTO para tests de listado.
     *
     * @return Lista con 3 StudentResponseDTO
     */
    public static List<StudentResponseDTO> createStudentResponseDTOList() {
        return List.of(
                createDefaultStudentResponseDTO(),
                createCustomStudentResponseDTO(SECOND_ID, SECOND_NAME, SECOND_LASTNAME, SECOND_EMAIL,
                        SECOND_CREATED_AT, SECOND_UPDATED_AT),
                createCustomStudentResponseDTO(THIRD_ID, THIRD_NAME, THIRD_LASTNAME, THIRD_EMAIL,
                        THIRD_CREATED_AT, THIRD_UPDATED_AT)
        );
    }

}
