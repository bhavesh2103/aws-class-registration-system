package com.example.registration.services;

import com.example.registration.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class StudentService {
    @Autowired
    private  DynamoDbClient dynamoDbClient;
    private final String tableName; // Name of your DynamoDB table ("Student_Data")

    public StudentService() {
        this.tableName = "StudentData"; // Set the table name here
    }

    public void addStudent(Student student) {
        // Create a PutItemRequest to add a student to DynamoDB
        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(createStudentItem(student))
                .build();

        // Use the DynamoDB client to put the item
        dynamoDbClient.putItem(request);
    }

    private Map<String, AttributeValue> createStudentItem(Student student) {
        java.util.Map<String, AttributeValue> item = new HashMap<>();
        item.put("studentId", AttributeValue.builder().s(student.getStudentId()).build());
        item.put("username", AttributeValue.builder().s(student.getUserName()).build());
        item.put("password", AttributeValue.builder().s(student.getPassword()).build());
        item.put("name", AttributeValue.builder().s(student.getName()).build());
        item.put("courseMajor", AttributeValue.builder().s(student.getCourseMajor()).build());
        item.put("workExperience", AttributeValue.builder().s(student.getWorkExperience()).build());
        item.put("projects", AttributeValue.builder().s(student.getProjects()).build());
        if (student.getPastCourseList() != null) {
            item.put("pastCourseList", AttributeValue.builder().l(
                    student.getPastCourseList().stream()
                            .map(course -> AttributeValue.builder().s(course).build())
                            .collect(Collectors.toList())
            ).build());
        }
        if (student.getPreferenceList() != null) {
            item.put("preferenceList", AttributeValue.builder().l(
                    student.getPreferenceList().stream()
                            .map(selected_course -> AttributeValue.builder().s(selected_course).build())
                            .collect(Collectors.toList())
            ).build());
        }
        if (student.getReferences() != null && !student.getReferences().isEmpty()) {
            Map<String, AttributeValue> referralsMap = new HashMap<>();
            for (Map.Entry<String, Integer> entry : student.getReferences().entrySet()) {
                referralsMap.put(entry.getKey(), AttributeValue.builder().n(Integer.toString(entry.getValue())).build());
            }
            item.put("references", AttributeValue.builder().m(referralsMap).build());
        }
        if (student.getFinalCourses() != null) {
            item.put("finalCourses", AttributeValue.builder().l(
                    student.getFinalCourses().stream()
                            .map(final_course -> AttributeValue.builder().s(final_course).build())
                            .collect(Collectors.toList())
            ).build());
        }

        return item;
    }
    public Student getStudent(String studentID){
        GetItemResponse response = dynamoDbClient.getItem(GetItemRequest.builder().tableName(tableName).key(Collections.singletonMap("studentId", AttributeValue.builder().s(studentID).build())).build());

        if (response.hasItem())
            return convertDDBItemToStudent(response.item());
        else
            return null;
    }

    public String getStudentMajor(String studentID){
        Student student = getStudent(studentID);

        return student.getCourseMajor();
    }

    public List<String> getPastSubjects(String studentID){
        Student student = getStudent(studentID);
        return student.getPastCourseList();
    }

    public List<Student> getAllStudents(){
        ScanResponse response = dynamoDbClient.scan(ScanRequest.builder().tableName(tableName).build());
        List<Student> studentList = new ArrayList<>();
        if (response.hasItems())
            for(Map<String,AttributeValue> item : response.items()){
                 studentList.add(convertDDBItemToStudent(item));
            }
        else
            return null;
        return studentList;
    }

    private  Student convertDDBItemToStudent(Map<String,AttributeValue> item){
        Student student = new Student();
        student.setStudentId(item.get("studentId").s());
        student.setPassword(item.get("password").s());
        student.setUserName(item.get("username").s());
        student.setProjects(item.get("projects").s());
        student.setName(item.get("name").s());
        student.setCourseMajor(item.get("courseMajor").s());
        student.setWorkExperience(item.get("workExperience").s());
        student.setReferences( item.get("references").m().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry-> Integer.valueOf(entry.getValue().n()))));
        student.setPastCourseList( item.get("pastCourseList").l().stream().map(AttributeValue::s).toList() );
        student.setPreferenceList( item.get("preferenceList").l().stream().map(AttributeValue::s).toList() );
        if (item.containsKey("finalCourses")){
            student.setFinalCourses( item.get("finalCourses").l().stream().map(AttributeValue::s).toList() );
        }else {
            student.setFinalCourses(new ArrayList<>());
        }

        return  student;
    }




}
