package com.example.registration.controller;

import com.example.registration.model.Student;
import com.example.registration.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

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
    @RequestMapping("/register/update")
    public String updatePrefrenceList(@RequestBody String studentID,@RequestBody List<String> newPrefrenceList) {
        // Use the StudentRepository to add the student to DynamoDB
        Student student = studentService.getStudent(studentID);
        if( student!=null){
            studentService.addStudent(student);
            return "Success";
        }else{
            return "No such student found";
        }

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
