package com.example.registration.model;

public class Student {
    public Student(String student_id, String name, int age) {
        this.studentID = student_id;
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String studentID;
    private String name;
    private int age;


}
