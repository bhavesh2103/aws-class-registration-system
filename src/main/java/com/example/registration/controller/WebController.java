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

import java.util.*;

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
    @RequestMapping("/addCourses")
    public String addCourses(@RequestBody Courses course) {
        courseService.addCourses(course);
        return "Course Added Successfully";
    }


    @GetMapping
    @RequestMapping("/login")
    public  Map<String, String> login(@RequestParam String username, String password) {
        List<Student> studentData = new ArrayList<>();
        studentData = studentService.getAllStudents();
        for (Student studentDatum : studentData) {
            if (studentDatum.getUserName().equals(username) && studentDatum.getPassword().equals(password)) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("status", "true");
                response.put("studentId", studentDatum.getStudentId());
                return response;
            }
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login failed");
        response.put("status", "false");
        return response;
    }

    @GetMapping
    @RequestMapping("/executeMatching")
    public Map<String, PriorityQueue<CoursePriority>> execute() {
        return  matchingService.executeAlgorithm();
    }

    @GetMapping
    @RequestMapping("/getCourseList")
    public List<Courses> getCourses(@RequestParam String studentId, String courseCode){
        List<Courses> coursesList = new ArrayList<>();
        System.out.println(studentId);
        System.out.println(courseCode);
        if(courseCode == null){
            System.out.println("Called Without Course Code");
            String courseMajor = studentService.getStudentMajor(studentId);
            coursesList = courseService.getCourseData(courseMajor);
        }
        else{
            System.out.println("Called Course Code");
            coursesList = courseService.getCoursesStartingWithCourseCode(courseCode);
        }
        return coursesList;
    }
}
