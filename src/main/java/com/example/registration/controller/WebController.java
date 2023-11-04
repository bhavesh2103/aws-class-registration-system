package com.example.registration.controller;

import com.example.registration.model.Student;
import com.example.registration.services.StudentRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    private StudentRepository studentRepository;

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
            student = new Student("Sanky1299","Sanket Dumbare", 29 );
        }
        studentRepository.addStudent(student);
    }
}
