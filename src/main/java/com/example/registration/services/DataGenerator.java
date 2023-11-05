package com.example.registration.services;

import com.example.registration.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataGenerator {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;



    private static final String[] FIRST_NAMES = {
            "John", "Mary", "James", "Lisa", "David", "Sarah", "Michael", "Emily", "William", "Linda"
    };
    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Davis", "Jones", "Wilson", "Miller", "Moore", "Taylor", "Anderson"
    };
    private static final String[] COURSE_CODES = {"CSE101", "CSE445", "CSE601", "CSE602", "CSE603", "CSE604", "CSE605", "CSE606", "CSE607", "CSE608"};
    private static final String[] PAST_COURSE_CODES = {"CSE445","CSE101"};

    public void addRandomStudents(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            Student tempStudent = new Student();
            tempStudent.setStudentId(generateRandomId());
            tempStudent.setUserName(generateRandomUsername());
            tempStudent.setPassword(generateRandomPassword());
            tempStudent.setName(generateRandomName());
            tempStudent.setCourseMajor(generateRandomCourseMajor());
            tempStudent.setWorkExperience(generateRandomWorkExperience());
            tempStudent.setProjects(generateRandomProjects());

            List<String> pastCourseList = generateRandomPastCourses();
            tempStudent.setPastCourseList(pastCourseList);

            List<String> preferenceList = generateRandomPreferenceList();
            tempStudent.setPreferenceList(preferenceList);

            Map<String, Integer> references = generateRandomReferences();
            tempStudent.setReferences(references);

            // Add the generated student to the student service
            studentService.addStudent(tempStudent);
        }
    }


    private String generateRandomId() {

        return "S" + System.currentTimeMillis();
    }


    private String generateRandomUsername() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

        return "user" + firstName+"-"+lastName+random.nextInt(10);
    }


    private String generateRandomPassword() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return "password" + firstName+"-"+lastName+random.nextInt(10);
    }


    private String generateRandomName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;

    }


    private String generateRandomCourseMajor() {

        return "Computer Science";
    }


    private String generateRandomWorkExperience() {

        return "Intern at XYZ Company";
    }


    private String generateRandomProjects() {

        return "Developed a web application for project management";
    }


    private List<String> generateRandomPastCourses() {

        List<String> pastCourses = new ArrayList<>();
        Random random = new Random();


        int numPastCourses = random.nextInt(PAST_COURSE_CODES.length);

        for (int i = 0; i < numPastCourses; i++) {
            int index = random.nextInt(PAST_COURSE_CODES.length);
            pastCourses.add(PAST_COURSE_CODES[index]);
        }

        return pastCourses;
    }



    private List<String> generateRandomPreferenceList() {

        List<String> preferenceList = new ArrayList<>();
        Random random = new Random();
        int numPreferences = 10;
        for (int i = 0; i < numPreferences; i++) {
            int index = random.nextInt(COURSE_CODES.length);
            preferenceList.add(COURSE_CODES[index]);
        }
        HashSet<String> uniqueSet = new HashSet<>(preferenceList);
        preferenceList = new ArrayList<>(uniqueSet);

        return preferenceList;
    }


    private Map<String, Integer> generateRandomReferences() {

        Map<String, Integer> references = new HashMap<>();
        Random random = new Random();


        for (String courseCode : COURSE_CODES) {
            int numReferences = random.nextInt(11);
            references.put(courseCode, numReferences);
        }

        return references;
    }

}
