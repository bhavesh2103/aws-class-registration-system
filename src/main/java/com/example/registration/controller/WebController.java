package com.example.registration.controller;

import com.example.registration.model.CoursePriority;
import com.example.registration.model.Courses;
import com.example.registration.model.Student;
import com.example.registration.services.CourseService;
import com.example.registration.services.DataGenerator;
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
    private DataGenerator dataGenerator;

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
        studentService.addStudent(student);
        return "Success";
    }
    @GetMapping
    @RequestMapping("/getStudent")
    public Student getStudent(@RequestParam String studentId) {
        return studentService.getStudent(studentId);

    }
    @GetMapping
    @RequestMapping("/getAllStudents")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();

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
    public List<Student> execute() {
        Map<String, PriorityQueue<CoursePriority>> result = matchingService.executeAlgorithm();
        Map<String,List<String>> finalResult= new HashMap<>();
          for (String key : result.keySet()){
              PriorityQueue<CoursePriority> val = result.get(key);
              List<String> temp = new ArrayList<>();
              val.forEach(i->temp.add(i.stringValue));
              finalResult.put(key,temp);
          }
        updateDDB(finalResult);
        return getAllStudents();
    }

    private void updateDDB(Map<String, List<String>> finalResult) {
        Map<String,List<String>> studentAssignedMap = new HashMap<>();
        for (String course : finalResult.keySet()){
            List<String> assignedStudents = finalResult.get(course);
            for (String student : assignedStudents){
                List<String> tempList;
                List<String> val = studentAssignedMap.get(student);
                if ( val== null){
                    tempList= new ArrayList<>();
                }else {
                    tempList = val;
                }

               tempList.add(course);
               studentAssignedMap.put(student,new ArrayList<>(new HashSet<>(tempList)));
            }
        }
        for (String student : studentAssignedMap.keySet() ){
            List<String> assignedList= studentAssignedMap.get(student);
           Student student1 =  getStudent(student);
           student1.setFinalCourses(assignedList);
           addStudent(student1);
        }
    }


    @PostMapping
    @RequestMapping("/addStudents")
    public String addStudents(@RequestBody List<Student> studentList) {
        for(Student student  : studentList)
            studentService.addStudent(student);
        return "Success";
    }


    @GetMapping("/generateRandomStudents")
    public String addRandomStudents() {
        dataGenerator.addRandomStudents(10);
        return "Success";
    }

    @GetMapping
    @RequestMapping("/getCourseList")
    public List<Courses> getCourses(@RequestParam String studentId, String courseCode){
        List<Courses> coursesList = new ArrayList<>();
        System.out.println(studentId);
        System.out.println(courseCode);
        List<String> pastcourses = studentService.getPastSubjects(studentId);
        if(courseCode == null){
            System.out.println("Called Without Course Code");
            String courseMajor = studentService.getStudentMajor(studentId);
            coursesList = courseService.getCourseData(courseMajor,pastcourses);
        }
        else{
            System.out.println("Called Course Code");
            coursesList = courseService.getCoursesStartingWithCourseCode(courseCode,pastcourses);
        }
        return coursesList;

    }
}
