package com.example.registration.services;

import com.example.registration.model.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
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
        item.put("courseId", AttributeValue.builder().s(course.getCourseId()).build());
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
            item.put("eligibleMajors", AttributeValue.builder().l(
                    course.getEligibleMajors().stream()
                            .map(major -> AttributeValue.builder().s(major).build())
                            .collect(Collectors.toList())
            ).build());
        }

        return item;
    }

    private  Courses convertDDBItemToCourse(Map<String,AttributeValue> item){
        Courses course = new Courses();
        course.setCourseId(item.get("courseId").s());
        course.setCourseName(item.get("courseName").s());
        course.setPrerequisites( item.get("prerequisites").l().stream().map(AttributeValue::s).toList() );
        course.setCapacity(Integer.parseInt(item.get("capacity").n()));
        course.setDescription(item.get("description").s());
        course.setEligibleMajors(item.get("eligibleMajors").l().stream().map(AttributeValue::s).toList() );
        return  course;
    }

    public List<Courses> getAllCourses(){
        List<Courses> courses = new ArrayList<>();

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName("CourseData") // Replace with your table name
                .build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
        List<Map<String, AttributeValue>> items = scanResponse.items();
        for (Map<String, AttributeValue> item : items) {
            Courses course = convertDDBItemToCourse(item);
            courses.add(course);
        }
        return courses;
    }
    public List<Courses> getCourseData(String courseMajor, List<String> pastcourses){

        List<Courses> courses = new ArrayList<>();

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName("CourseData") // Replace with your table name
                .build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
        List<Map<String, AttributeValue>> items = scanResponse.items();
        for (Map<String, AttributeValue> item : items) {
            Courses course = convertDDBItemToCourse(item);
            //System.out.println(student.getUserName());
            //System.out.println(student.getPassword());
            boolean eligibleCourse = course.getEligibleMajors().contains(courseMajor);
            if(eligibleCourse){
                if(!pastcourses.contains(course.getCourseId())){
                    courses.add(course);
                }
            }
        }
        return courses;
    }

    public List<Courses> getCoursesStartingWithCourseCode(String courseCode, List<String> pastcourses) {
        List<Courses> courses = new ArrayList<>();

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName("CourseData") // Replace with your table name
                .build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
        List<Map<String, AttributeValue>> items = scanResponse.items();
        for (Map<String, AttributeValue> item : items) {
            Courses course = convertDDBItemToCourse(item);
            //System.out.println(student.getUserName());
            //System.out.println(student.getPassword());
            boolean coursevalue = course.getCourseId().startsWith(courseCode);
            if(coursevalue){
                if(!pastcourses.contains(course.getCourseId())){
                    courses.add(course);
                }
            }
        }

        return courses;
    }
}
