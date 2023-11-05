package com.example.registration.controller;

import com.example.registration.model.CoursePriority;
import com.example.registration.model.Courses;
import com.example.registration.model.Student;
import com.example.registration.services.CourseService;
import com.example.registration.services.MatchingService;
import com.example.registration.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.PriorityQueue;

@RestController
public class WebController {

    @Autowired
    private StudentService studentService;

    @Autowired
    MatchingService matchingService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/hello")
    public String hello(@RequestParam(name="name", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", name);
        return "Greetings "+name+" from Spring Boot!";
    }

    @PostMapping
    @RequestMapping("/register/update")
    public String updatePrefrenceList(@RequestBody Student student) {
        String studentID = student.getStudentId();
        // Use the StudentRepository to add the student to DynamoDB
        Student newStudent = studentService.getStudent(studentID);
        if( newStudent!=null){
            newStudent.setPreferenceList(student.getPreferenceList());
            studentService.addStudent(newStudent);
            return "Success";
        }else{
            return "No such student found";
        }

    }

    @PostMapping
    @RequestMapping("/addStudent")
    public String addStudent(@RequestBody Student student) {
        // Use the StudentRepository to add the student to DynamoDB
        studentService.addStudent(student);
        return "Success";
    }
    @GetMapping
    @RequestMapping("/getStudent")
    public Student addStudent(@RequestParam String studentId) {
        // Use the StudentRepository to add the student to DynamoDB
        return studentService.getStudent(studentId);

    }

    @PostMapping
    @RequestMapping("/courses")
    public String addCourses(@RequestBody Courses course) {
        courseService.addCourses(course);
        return "Course Added Successfully";
    }


    @GetMapping
    @RequestMapping("/login")
    public boolean login(@RequestParam String username, String password) {
        List<Student> studentData = new ArrayList<>();
        studentData = studentService.getAllStudents();
        for (Student studentDatum : studentData) {
            if (studentDatum.getUserName().equals(username) && studentDatum.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping
    @RequestMapping("/executeMatching")
    public Map<String, PriorityQueue<CoursePriority>> execute() {
        return  matchingService.executeAlgorithm();
    }
}
