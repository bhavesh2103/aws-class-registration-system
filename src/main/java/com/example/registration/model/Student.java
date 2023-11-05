package com.example.registration.model;

import java.util.List;
import java.util.Map;

public class Student {
    private String studentId;
    private String userName;
    private String password;
    private String name;
    private String courseMajor;
    private String workExperience;
    private String projects;
    private List<String> pastCourseList;
    private List<String> preferenceList;
    private Map<String, Integer> references;

    private List<String> finalCourses;
    public Student(){

    }
    public Student(String studentId, String userName, String password, String name, String courseMajor, String workExperience, String projects, List<String> pastCourseList, List<String> preferenceList, Map<String, Integer> references, List<String> finalCourses) {
        this.studentId = studentId;
        this.userName = userName;
        this.password = password; // TODO : Encrypt password
        this.name = name;
        this.courseMajor = courseMajor;
        this.workExperience = workExperience;
        this.projects = projects;
        this.pastCourseList = pastCourseList;
        this.preferenceList = preferenceList;
        this.references = references;
        this.finalCourses = finalCourses;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        this.courseMajor = courseMajor;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public List<String> getPastCourseList() {
        return pastCourseList;
    }

    public void setPastCourseList(List<String> pastCourseList) {
        this.pastCourseList = pastCourseList;
    }

    public Map<String, Integer> getReferences() {
        return references;
    }

    public void setReferences(Map<String, Integer> references) {
        this.references = references;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<String> getPreferenceList() {
        return preferenceList;
    }

    public void setPreferenceList(List<String> preferenceList) {
        this.preferenceList = preferenceList;
    }

    public List<String> getFinalCourses() {
        return finalCourses;
    }

    public void setFinalCourses(List<String> finalCourses) {
        this.finalCourses = finalCourses;
    }
}
