package com.lta.springboot.student_management.controller;

import com.lta.springboot.student_management.domain.dto.response.StudentResponseDTO;
import com.lta.springboot.student_management.service.interfaces.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
