package com.examora.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    // put final if want to don't change the refrence of courses or examAttempts
    private List<Course> courses = new ArrayList<>();
    private List<ExamAttempt> examAttempts = new ArrayList<>();

    public Student(int id, String name, String email) {
        super(id, name, email);
    }

    public Student(String name, String email) {
        super(name, email);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<ExamAttempt> getExamAttempts() {
        return examAttempts;
    }

    @Override
    public String toString() {
        return "Student { id : " + getId() + ", name : '" + getName() + "'}";
    }

}
