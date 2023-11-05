package com.example.registration.services;

import com.example.registration.model.Courses;
import com.example.registration.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private DynamoDbClient dynamoDbClient;
    private final String tableName; // Name of your DynamoDB table ("Student_Data")

    public CourseService() {
        this.tableName = "Course_Data"; // Set the table name here
    }

    public void addCourses(Courses course) {
        // Create a PutItemRequest to add a student to DynamoDB
        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(createCourseItem(course))
                .build();

        // Use the DynamoDB client to put the item
        dynamoDbClient.putItem(request);
    }

    private Map<String, AttributeValue> createCourseItem(Courses course){
        java.util.Map<String, AttributeValue> item = new HashMap<>();
        item.put("course_id", AttributeValue.builder().s(course.getCourse_id()).build());
        item.put("courseName", AttributeValue.builder().s(course.getCourseName()).build());
        if (course.getPrerequisites() != null) {
            item.put("prerequisites", AttributeValue.builder().l(
                    course.getPrerequisites().stream()
                            .map(prerequire -> AttributeValue.builder().s(prerequire).build())
                            .collect(Collectors.toList())
            ).build());
        }
        item.put("capacity", AttributeValue.builder().n(Integer.toString(course.getCapacity())).build());
        item.put("description", AttributeValue.builder().s(course.getDescription()).build());
        if (course.getEligible_majors() != null) {
            item.put("eligible_majors", AttributeValue.builder().l(
                    course.getEligible_majors().stream()
                            .map(major -> AttributeValue.builder().s(major).build())
                            .collect(Collectors.toList())
            ).build());
        }

        return item;
    }
}
