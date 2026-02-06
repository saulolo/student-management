package com.lta.springboot.student_management.controller;

import com.lta.springboot.student_management.domain.dto.request.StudentRequestDTO;
import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;
import com.lta.springboot.student_management.service.interfaces.IStudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/students")
public class StudentController {

    private final IStudentService iStudentService;


    public StudentController(IStudentService studentService) {
        this.iStudentService = studentService;
    }


    /**
     * Muestra la lista de estudiantes en la vista 'student/students'.
     *
     * @param model Modelo para la vista
     * @return nombre del template a renderizar
     */
    @GetMapping("/listStudents")
    public String listStudents(Model model) {
        List<StudentResponseDTO> students = iStudentService.findAllStudents();
        model.addAttribute("students", students);
        return "student/students";
    }

    /**
     * Muestra el formulario para crear un nuevo estudiante.
     *
     * @param model modelo para pasar datos a la vista
     * @return nombre de la vista del formulario (student/student_form)
     */
    @GetMapping("/new")
    public String showNewStudentForm(Model model) {
        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        model.addAttribute("student", studentRequestDTO );
        return "student/student_form";
    }

    /**
     * Procesa el formulario de creación de un nuevo estudiante.
     * Si la validación es exitosa, guarda el estudiante y redirige a la lista.
     * Si hay errores, vuelve al formulario mostrando los mensajes de validación.
     *
     * @param studentRequestDTO  datos del estudiante a crear (validados con @Valid)
     * @param result             resultado de las validaciones de Bean Validation
     * @param redirectAttributes atributos flash para mensajes entre redirecciones
     * @return redirección a la lista si éxito, al formulario si error de validación,
     *         al formulario nuevo si error de sistema
     */
    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") StudentRequestDTO studentRequestDTO,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.warn("Errores de validación al crear estudiante: {}", result.getAllErrors());
            return "student/student_form";
        }

        try {
            // Guardar el estudiante
            StudentResponseDTO savedStudent = iStudentService.createStudent(studentRequestDTO);

            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("successMessage",
                    "Estudiante creado exitosamente: " + savedStudent.name() + " " + savedStudent.lastName());

            log.info("Estudiante guardado con éxito: {}", savedStudent.idStudent());

            // Redirigir a la lista de estudiantes
            return "redirect:/students/listStudents";

        } catch (Exception e) {
            log.error("Error al guardar estudiante", e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error al crear el estudiante. Por favor intente nuevamente.");
            return "redirect:/students/new";
        }
    }

}
