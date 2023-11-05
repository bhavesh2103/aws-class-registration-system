package com.example.registration.model;

import java.util.List;
import java.util.Map;

public class Student {
    private String studentID;
    private String userName;
    private String password;
    private String name;
    private String course_major;
    private String work_experience;
    private String projects;
    private List<String> pastCourses;
    private List<String> priorityCourses;
    private Map<String, Integer> referalls;


    public Student(String studentID, String userName, String password, String name, String course_major, String work_experience, String projects, List<String> pastCourses, List<String> priorityCourses, Map<String, Integer> referalls) {
        this.studentID = studentID;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.course_major = course_major;
        this.work_experience = work_experience;
        this.projects = projects;
        this.pastCourses = pastCourses;
        this.priorityCourses = priorityCourses;
        this.referalls = referalls;
    }

    public String getCourse_major() {
        return course_major;
    }

    public void setCourse_major(String course_major) {
        this.course_major = course_major;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public List<String> getPastCourses() {
        return pastCourses;
    }

    public void setPastCourses(List<String> pastCourses) {
        this.pastCourses = pastCourses;
    }

    public Map<String, Integer> getReferalls() {
        return referalls;
    }

    public void setReferalls(Map<String, Integer> referalls) {
        this.referalls = referalls;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public String getStudentID() {
        return studentID;
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


    public List<String> getPriorityCourses() {
        return priorityCourses;
    }

    public void setPriorityCourses(List<String> priorityCourses) {
        this.priorityCourses = priorityCourses;
    }
}
