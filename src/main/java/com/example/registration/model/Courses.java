package com.example.registration.model;

import java.util.List;

public class Courses {
    private String course_id;
    private String courseName;
    private List<String> prerequisites;

    private int capacity;
    private String description;
    private List<String> eligible_majors;

    public Courses(String course_id, String courseName, List<String> prerequisites, int capacity, String description, List<String> eligible_majors) {
        this.course_id = course_id;
        this.courseName = courseName;
        this.prerequisites = prerequisites;
        this.capacity = capacity;
        this.description = description;
        this.eligible_majors = eligible_majors;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
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

    public List<String> getEligible_majors() {
        return eligible_majors;
    }

    public void setEligible_majors(List<String> eligible_majors) {
        this.eligible_majors = eligible_majors;
    }
}
