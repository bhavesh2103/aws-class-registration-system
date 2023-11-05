package com.example.registration.services;

import com.example.registration.model.Courses;
import com.example.registration.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private DynamoDbClient dynamoDbClient;
    private final String tableName; // Name of your DynamoDB table ("Student_Data")

    public CourseService() {
        this.tableName = "CourseData"; // Set the table name here
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
        item.put("course_id", AttributeValue.builder().s(course.getCourseId()).build());
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
        if (course.getEligibleMajors() != null) {
            item.put("eligible_majors", AttributeValue.builder().l(
                    course.getEligibleMajors().stream()
                            .map(major -> AttributeValue.builder().s(major).build())
                            .collect(Collectors.toList())
            ).build());
        }

        return item;
    }

//    private  Courses convertDDBItemToCourse(Map<String,AttributeValue> item){
//        Courses course = new Courses();
//        course.setStudentId(item.get("studentId").s());
//        course.setPassword(item.get("password").s());
//        course.setUserName(item.get("username").s());
//        course.setProjects(item.get("projects").s());
//        course.setName(item.get("name").s());
//        course.setCourseMajor(item.get("courseMajor").s());
//        course.setWorkExperience(item.get("workExperience").s());
//        course.setReferences( item.get("references").m().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry-> Integer.valueOf(entry.getValue().n()))));
//        course.setPastCourseList( item.get("pastCourseList").l().stream().map(AttributeValue::s).toList() );
//        course.setPreferenceList( item.get("preferenceList").l().stream().map(AttributeValue::s).toList() );
//        return  course;
//    }

    public List<Courses> getCourseData(){

        List<Courses> courses = new ArrayList<>();

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName("CourseData") // Replace with your table name
                .build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
        List<Map<String, AttributeValue>> items = scanResponse.items();

        for (Map<String, AttributeValue> item : items) {
            //Courses course = convertDDBItemToCourse(item);
            //System.out.println(student.getUserName());
            //System.out.println(student.getPassword());
            //courses.add(course);
        }

        return courses;
    }
}
