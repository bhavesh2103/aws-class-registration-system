package com.example.registration.services;

import com.example.registration.model.CoursePriority;
import com.example.registration.model.Courses;
import com.example.registration.model.Student;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    @Autowired
    StudentService   studentService;
    @Autowired
    CourseService courseService;
    public Map<String, PriorityQueue<CoursePriority>> stableMatching(List<Student> studentList , List<Courses> coursesList) {
        Map<String, String> studentAssignments = new HashMap<>();

        Map<String, PriorityQueue<CoursePriority>> courseAssignments = new HashMap<>();
        Map<String, Courses> courseMap = new HashMap<>();

        for (Courses course : coursesList) {
            PriorityQueue<CoursePriority> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.intValue));
            courseAssignments.put(course.getCourseId(), minHeap);
            courseMap.put(course.getCourseId(),course);
        }
        
        Map<String, Integer> studentCount = new HashMap<>();
        for (Student student : studentList ) {
            studentCount.put(student.getStudentId(), 3);
        }
        
        int studentLeft = studentList.size();
        int flag = 0;
        int count = studentLeft * studentLeft - 1;
        
        while (studentLeft > 0) {
            if (flag == count) {
                break;
            }
            flag++;
            
            for (Student student  : studentList) {
                String id=student.getStudentId();
                if (studentCount.get(id) == 0) {
                    continue;
                }
                int prevCount = 0;
                while (studentCount.get(id) > 0) {
                    if (prevCount == studentCount.get(id) ){
                        //rendomly assign course to this student
                        for( String courseId : courseMap.keySet()){
                            int cap = courseMap.get(courseId).getCapacity();
                            if (cap > 0){
                                courseAssignments.get(courseId).offer(new CoursePriority(coursePreference(courseMap.get(courseId), student),id));
                                break;
                            }
                        }

                        studentCount.put(id, studentCount.get(id) - 1);
                        break;
                    }
                    prevCount = studentCount.get(id);
                    List<String> delCourses = new ArrayList<>();
                    List<String> studentPreferenceList = student.getPreferenceList();
                    
                    for (String courseId : studentPreferenceList) {
                        int currentPriority = coursePreference(courseMap.get(courseId), student);
                        delCourses.add(courseId);
                        int courseCap= courseMap.get(courseId).getCapacity();
                        if (courseCap > 0) {
                            courseAssignments.get(courseId).offer(new CoursePriority(currentPriority,id));
                            studentCount.put(id, studentCount.get(id) - 1);
                            courseMap.get(courseId).setCapacity(courseCap - 1);
                            break;
                        } else {
                            int minPriority = courseAssignments.get(courseId).peek().intValue;
                            String minStudent = courseAssignments.get(courseId).peek().stringValue;
                            Student minStudentData = null;
                            if (minPriority == currentPriority) {
                                for (Student tempStudent  : studentList) {
                                    if (tempStudent.getStudentId().equals(minStudent)){
                                        minStudentData = tempStudent;
                                        break;
                                    }
                                }
                                double llmPrefMinStudent = getPreferenceLLM(courseMap.get(courseId),  minStudentData);
                                double llmPrefCurStudent = getPreferenceLLM(courseMap.get(courseId), student);
                                
                                if (llmPrefMinStudent < llmPrefCurStudent) {
                                    courseAssignments.get(courseId).poll();
                                    studentCount.put(minStudent, studentCount.get(minStudent) + 1);
                                    
                                    if (studentCount.get(minStudent) == 1) {
                                        studentLeft++;
                                    }
                                    
                                    courseAssignments.get(courseId).offer(new CoursePriority(currentPriority,id));
                                    studentCount.put(id, studentCount.get(id) - 1);
                                    break;
                                }
                            }
                            
                            if (minPriority < currentPriority) {
                                courseAssignments.get(courseId).remove(0);
                                studentCount.put(minStudent, studentCount.get(minStudent) + 1);
                                
                                if (studentCount.get(minStudent) == 1) {
                                    studentLeft++;
                                }
                                
                                courseAssignments.get(courseId).offer(new CoursePriority(currentPriority,id));
                                studentCount.put(id, studentCount.get(id) - 1);
                                break;
                            }
                        }
                    }
                    
                    for (String course : delCourses) {
                        List<String> temp = new ArrayList<>(student.getPreferenceList());
                        temp.remove(course);
                        student.setPreferenceList(temp);

                    }
                }
                
                if (studentCount.get(id) == 0) {
                    studentLeft--;
                }
            }
        }
        
        return courseAssignments;
    }

    public void randomAssignCourse(Student student, Map<String, Courses> courseMap){

    }
    
    public  int coursePreference(Courses course, Student student) {
        int summ = 0;
        List<String> preqCourses =course.getPrerequisites();
        List<String> pastCourses = student.getPastCourseList();
        
        int numPreq = preqCourses.size();
        int countPreq = 0;
        
        for (String pastCourse : pastCourses) {
            if (preqCourses.contains(pastCourse)) {
                countPreq++;
            }
        }
        
        summ = summ + (int)((countPreq / numPreq) * 10);
        
        Map<String, Integer> reference = student.getReferences();
        
        if (reference.get(course.getCourseId()) != null) {
            summ += reference.get(course.getCourseId());
        }
        
        return summ;
    }
    
    public  int getPreferenceLLM(Courses course, Student student) {
        // Implement your logic for LLM preference calculation here
        String studentProject = student.getProjects();
        String courseName = course.getCourseName();
        String llmMessage = "Subject is " + courseName + ". String is: " + studentProject;
        int score = 0;
        try{
            // Set your OpenAI API key
            String apiKey = "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";

            // Define the API endpoint
            String endpoint = "https://api.openai.com/v1/chat/completions";

            // Define the request message
            Map<String, Object> message = new HashMap<>();
            message.put("role", "system");
            message.put("content", "Analyse string to see this project or work is relevant to the subject given. Give a rating from 0 to 10, with 0 being least relevant and 10 being most relevant. Return only a number and nothing else. Be strict in this assessment, if a subject is not relevant, the score should be below 5.");

            Map<String, Object> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", llmMessage);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");  // Choose the appropriate GPT-3 engine
            requestBody.put("messages", new Object[]{message, userMessage});

            // Convert the request body to a JSON string
            ObjectMapper om = new ObjectMapper();
            String jsonRequestBody = om.writeValueAsString(requestBody);

            // Create a URL object
            URL url = new URL(endpoint);

            // Create an HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the HTTP request method to POST
            connection.setRequestMethod("POST");

            // Set the request headers, including the API key
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(jsonRequestBody.length()));
            connection.setDoOutput(true);

            // Write the JSON request body to the connection's output stream
            try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                out.writeBytes(jsonRequestBody);
            }

            // Get the HTTP response code
            int responseCode = connection.getResponseCode();

            // Read the response from the connection's input stream
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // get response
                JsonNode responseNode = om.readTree(String.valueOf(response));
                score = responseNode.get("choices").get(0).get("message").get("content").asInt();
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return score;
    }
    
//    public static void main(String[] args) {
//        Map<String, List<String>> studentPrefs = new HashMap<>();
//        // Initialize studentPrefs with randomized preferences
//
//        Map<String, Integer> courseCapacity = new HashMap<>();
//        // Initialize courseCapacity with randomized values
//
//        Map<String, List<String>> assignments = stableMatching(studentPrefs, courseCapacity);
//        System.out.println(assignments);
//    }

    public Map<String, PriorityQueue<CoursePriority>> executeAlgorithm(){
        List<Student> studentList = studentService.getAllStudents();
        List<Courses> coursesList = courseService.getAllCourses();
//        Map<String,List<String>> studentPrefs=new HashMap<>();
//        for(Student student : studentList ){
//            studentPrefs.put(student.getStudentId(),student.getPreferenceList());
//        }
        Map<String, PriorityQueue<CoursePriority>> matching = stableMatching(studentList, coursesList);
        return matching;

    }



}
