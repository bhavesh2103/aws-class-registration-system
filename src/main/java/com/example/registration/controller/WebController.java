package com.example.registration.controller;

import com.example.registration.model.Student;
import com.example.registration.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/hello")
    public String hello(@RequestParam(name="name", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", name);
        return "Greetings "+name+" from Spring Boot!";
    }

    @PostMapping
    @RequestMapping("/students")
    public void addStudent(@RequestBody Student student) {
        // Use the StudentRepository to add the student to DynamoDB
        if(student == null){
            student = new Student("12345678","Sanket Dumbare", 29 );
        }
        studentService.addStudent(student);
    }
}
