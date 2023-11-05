package com.example.registration.model;

import java.util.List;

public class Courses {
    private String courseId;
    private String courseName;
    private List<String> prerequisites;

    private int capacity;
    private String description;
    private List<String> eligibleMajors;

    public Courses(String courseId, String courseName, List<String> prerequisites, int capacity, String description, List<String> eligibleMajors) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.prerequisites = prerequisites;
        this.capacity = capacity;
        this.description = description;
        this.eligibleMajors = eligibleMajors;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getEligibleMajors() {
        return eligibleMajors;
    }

    public void setEligibleMajors(List<String> eligibleMajors) {
        this.eligibleMajors = eligibleMajors;
    }
}
