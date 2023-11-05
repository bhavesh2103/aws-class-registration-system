package com.example.registration.services;

import java.util.*;

public class MatchingService {
    public static Map<String, List<String>> stableMatching(Map<String, List<String>> studentPrefs, Map<String, Integer> courseCapacity) {
        Map<String, String> studentAssignments = new HashMap<>();
        Map<String, List<String>> courseAssignments = new HashMap<>();
        
        for (String course : courseCapacity.keySet()) {
            courseAssignments.put(course, new ArrayList<>());
        }
        
        Map<String, Integer> studentCount = new HashMap<>();
        for (String student : studentPrefs.keySet()) {
            studentCount.put(student, 3);
        }
        
        int studentLeft = studentPrefs.size();
        int flag = 0;
        int count = studentLeft * studentLeft - 1;
        
        while (studentLeft > 0) {
            if (flag == count) {
                break;
            }
            flag++;
            
            for (String student : studentPrefs.keySet()) {
                if (studentCount.get(student) == 0) {
                    continue;
                }
                
                while (studentCount.get(student) > 0) {
                    List<String> delCourses = new ArrayList<>();
                    List<String> coursePreferences = studentPrefs.get(student);
                    
                    for (String course : coursePreferences) {
                        double currentPriority = coursePreference(course, student);
                        delCourses.add(course);
                        
                        if (courseCapacity.get(course) > 0) {
                            courseAssignments.get(course).add(currentPriority + ":" + student);
                            studentCount.put(student, studentCount.get(student) - 1);
                            courseCapacity.put(course, courseCapacity.get(course) - 1);
                            break;
                        } else {
                            double minPriority = Double.parseDouble(courseAssignments.get(course).get(0).split(":")[0]);
                            String minStudent = courseAssignments.get(course).get(0).split(":")[1];
                            
                            if (minPriority == currentPriority) {
                                double llmPrefMinStudent = getPreferenceLLM(course, minStudent);
                                double llmPrefCurStudent = getPreferenceLLM(course, student);
                                
                                if (llmPrefMinStudent < llmPrefCurStudent) {
                                    courseAssignments.get(course).remove(0);
                                    studentCount.put(minStudent, studentCount.get(minStudent) + 1);
                                    
                                    if (studentCount.get(minStudent) == 1) {
                                        studentLeft++;
                                    }
                                    
                                    courseAssignments.get(course).add(currentPriority + ":" + student);
                                    studentCount.put(student, studentCount.get(student) - 1);
                                    break;
                                }
                            }
                            
                            if (minPriority < currentPriority) {
                                courseAssignments.get(course).remove(0);
                                studentCount.put(minStudent, studentCount.get(minStudent) + 1);
                                
                                if (studentCount.get(minStudent) == 1) {
                                    studentLeft++;
                                }
                                
                                courseAssignments.get(course).add(currentPriority + ":" + student);
                                studentCount.put(student, studentCount.get(student) - 1);
                                break;
                            }
                        }
                    }
                    
                    for (String course : delCourses) {
                        studentPrefs.get(student).remove(course);
                    }
                }
                
                if (studentCount.get(student) == 0) {
                    studentLeft--;
                }
            }
        }
        
        return courseAssignments;
    }
    
    public static double coursePreference(String course, String student) {
        double summ = 0.0;
        List<String> preqCourses = new ArrayList<>();
        List<String> pastCourses = new ArrayList<>();
        
        int numPreq = preqCourses.size();
        int countPreq = 0;
        
        for (String pastCourse : pastCourses) {
            if (preqCourses.contains(pastCourse)) {
                countPreq++;
            }
        }
        
        summ = summ + (countPreq / numPreq) * 10;
        
        Map<String, Integer> reference = new HashMap<>();
        
        if (reference.get(course) != null && reference.get(course) == 1) {
            summ += 10.0;
        }
        
        return summ;
    }
    
    public static double getPreferenceLLM(String course, String student) {
        // Implement your logic for LLM preference calculation here
        return 0.0;
    }
    
    public static void main(String[] args) {
        Map<String, List<String>> studentPrefs = new HashMap<>();
        // Initialize studentPrefs with randomized preferences
        
        Map<String, Integer> courseCapacity = new HashMap<>();
        // Initialize courseCapacity with randomized values
        
        Map<String, List<String>> assignments = stableMatching(studentPrefs, courseCapacity);
        System.out.println(assignments);
    }
}
