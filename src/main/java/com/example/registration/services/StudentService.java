package com.example.registration.services;

import com.example.registration.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class StudentService {
    @Autowired
    private  DynamoDbClient dynamoDbClient;
    private final String tableName; // Name of your DynamoDB table ("Student_Data")

    public StudentService() {
        this.tableName = "Student_Data"; // Set the table name here
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
        item.put("student_id", AttributeValue.builder().s(student.getStudentID()).build());
        item.put("username", AttributeValue.builder().s(student.getUserName()).build());
        item.put("password", AttributeValue.builder().s(student.getPassword()).build());
        item.put("name", AttributeValue.builder().s(student.getName()).build());
        item.put("course_major", AttributeValue.builder().s(student.getCourse_major()).build());
        item.put("work_experience", AttributeValue.builder().s(student.getWork_experience()).build());
        item.put("projects", AttributeValue.builder().s(student.getProjects()).build());
        if (student.getPastCourses() != null) {
            item.put("past_courses", AttributeValue.builder().l(
                    student.getPastCourses().stream()
                            .map(course -> AttributeValue.builder().s(course).build())
                            .collect(Collectors.toList())
            ).build());
        }
        if (student.getPriorityCourses() != null) {
            item.put("priorityCourses", AttributeValue.builder().l(
                    student.getPriorityCourses().stream()
                            .map(selected_course -> AttributeValue.builder().s(selected_course).build())
                            .collect(Collectors.toList())
            ).build());
        }
        if (student.getReferalls() != null && !student.getReferalls().isEmpty()) {
            Map<String, AttributeValue> referralsMap = new HashMap<>();
            for (Map.Entry<String, Integer> entry : student.getReferalls().entrySet()) {
                referralsMap.put(entry.getKey(), AttributeValue.builder().n(Integer.toString(entry.getValue())).build());
            }
            item.put("referrals", AttributeValue.builder().m(referralsMap).build());
        }

        return item;
    }
    public Student getStudent(String studentID){
        GetItemResponse response = dynamoDbClient.getItem(GetItemRequest.builder().tableName(tableName).key(Collections.singletonMap("student_id", AttributeValue.builder().s(studentID).build())).build());

        if (response.hasItem())
            return convertDDBItemToStudent(response.item());
        else
            return null;
    }
    private  Student convertDDBItemToStudent(Map<String,AttributeValue> item){
        Student student = new Student();
        student.setStudentID(item.get("student_id").s());
        student.setName(item.get("student_id").s());
        return  student;
    }
}
