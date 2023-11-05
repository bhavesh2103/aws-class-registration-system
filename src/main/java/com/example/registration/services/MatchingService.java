package com.example.registration.services;

import com.example.registration.model.CoursePriority;
import com.example.registration.model.Courses;
import com.example.registration.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchingService {

    @Autowired
    StudentService   studentService;
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
                
                while (studentCount.get(id) > 0) {
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
                            
                            if (minPriority == currentPriority) {
                                double llmPrefMinStudent = getPreferenceLLM(courseMap.get(courseId), student); // TODO : use min student instead
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
                        student.getPreferenceList().remove(course);
                    }
                }
                
                if (studentCount.get(id) == 0) {
                    studentLeft--;
                }
            }
        }
        
        return courseAssignments;
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
        
        if (reference.get(course.getCourseId()) != null && reference.get(course.getCourseId()) == 1) {
            summ += 10;
        }
        
        return summ;
    }
    
    public  int getPreferenceLLM(Courses course, Student student) {
        // Implement your logic for LLM preference calculation here
        return 0;
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
//        Map<String,List<String>> studentPrefs=new HashMap<>();
//        for(Student student : studentList ){
//            studentPrefs.put(student.getStudentId(),student.getPreferenceList());
//        }
        Map<String, PriorityQueue<CoursePriority>> matching = stableMatching(studentList, null);
        return matching;

    }



}
