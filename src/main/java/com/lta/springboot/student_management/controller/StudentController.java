package com.lta.springboot.student_management.controller;

import com.lta.springboot.student_management.domain.dto.request.StudentRequestDTO;
import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;
import com.lta.springboot.student_management.service.interfaces.IStudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("student", studentRequestDTO);
        model.addAttribute("formTitle", "Nuevo Estudiante");
        model.addAttribute("isEditMode", false);
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
     * al formulario nuevo si error de sistema
     */
    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") StudentRequestDTO studentRequestDTO,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.warn("Errores de validación al crear estudiante: {}", result.getAllErrors());
            model.addAttribute("formTitle", "Nuevo Estudiante");
            model.addAttribute("isEditMode", false);
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

    /**
     * Muestra el formulario para editar un estudiante existente.
     *
     * @param id    identificador del estudiante a editar
     * @param model modelo para pasar datos a la vista
     * @return nombre de la vista del formulario (student/student_form)
     */
    @GetMapping("/edit/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        log.info("Mostrando formulario de edición para estudiante con ID: {}", id);
        // Buscar el estudiante
        StudentResponseDTO studentResponseDTO = iStudentService.findStudentById(id);

        // Convertir ResponseDTO a RequestDTO para el formulario
        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setName(studentResponseDTO.name());
        studentRequestDTO.setLastName(studentResponseDTO.lastName());
        studentRequestDTO.setEmail(studentResponseDTO.email());

        model.addAttribute("student", studentRequestDTO);
        model.addAttribute("idStudent", id);
        model.addAttribute("formTitle", "Editar Estudiante");
        model.addAttribute("isEditMode", true);

        return "student/student_form";
    }

    /**
     * Procesa el formulario de edición de un estudiante existente.
     *
     * @param id                 identificador del estudiante a actualizar
     * @param studentRequestDTO  datos actualizados del estudiante
     * @param result             resultado de las validaciones
     * @param model              modelo para la vista
     * @param redirectAttributes atributos flash para mensajes
     * @return redirección a la lista si éxito, al formulario si error
     */
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") StudentRequestDTO studentRequestDTO,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.warn("Errores de validación al actualizar estudiante con ID: {} {}", id, result.getAllErrors());
            model.addAttribute("idStudent", id);
            model.addAttribute("formTitle", "Editar Estudiante");
            model.addAttribute("isEditMode", true);
            return "student/student_form";
        }

        try {
            StudentResponseDTO updatedStudent = iStudentService.updateStudent(id, studentRequestDTO);

            redirectAttributes.addFlashAttribute("successMessage", "Estudiante actualizado exitosamente "
                    + updatedStudent.name() + " " + updatedStudent.lastName());
            log.info("Estudiante actualizado con éxito. {}", updatedStudent.idStudent());

            return "redirect:/students/listStudents";

        } catch (Exception e) {
            log.error("Error al actualizar estudiante ID: {}", id, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar el estudiante, " +
                    "por favor intenta de nuevo.");
            return "redirect:/students/edit/" + id;
        }
    }

    /**
     * Elimina un estudiante del sistema.
     *
     * @param id                 identificador del estudiante a eliminar
     * @param redirectAttributes atributos flash para mensajes
     * @return redirección a la lista de estudiantes
     */
    @PostMapping("/delete/{id}")
    public String deleteStudentById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar estudiante con ID: {}", id);

        try {
            // Obtener datos antes de eliminar (para el mensaje)
            StudentResponseDTO studentResponseDTO = iStudentService.findStudentById(id);

            // Eliminar el estudiante
            iStudentService.deleteStudentById(id);

            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("successMessage", "Estudiante eliminado exitosamente "
                    + studentResponseDTO.name() + " " + studentResponseDTO.lastName());
            log.info("El Estudiante con ID: {}, se ha eliminado con éxito.", id);

        } catch (Exception e) {
            log.error("Error al eliminar estudiante ID: {}", id, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar un estudiante, " +
                    "por favor intentalo nuevamente.");
        }

        return "redirect:/students/listStudents";
    }

}
