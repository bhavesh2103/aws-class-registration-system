package com.example.registration.services;

import com.example.registration.model.Student;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentRepository {
    private final DynamoDbClient dynamoDbClient;
    private final String tableName; // Name of your DynamoDB table ("Student_Data")

    public StudentRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
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
        item.put("StudentID", AttributeValue.builder().s(student.getStudentID()).build());
        item.put("Name", AttributeValue.builder().s(student.getName()).build());
        item.put("Age", AttributeValue.builder().n(Integer.toString(student.getAge())).build());
        return item;
    }
}
