package com.examora.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private int id;
    private String title;
    private Doctor doctor;
    private Course course;
    private List<Question> questions = new ArrayList<>();

    public QuestionBank(int id, String title, Course course, Doctor doctor) {
        setId(id);
        setTitle(title);
        setCourse(course);
        setDoctor(doctor);
    }

    public QuestionBank(String title, Course course, Doctor doctor) {
        this(0, title, course, doctor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be negative.");
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Title is required.");
        this.title = title.trim();
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null.");
        }
        if (questions.contains(question)) {
            throw new IllegalArgumentException("Question already exists in this bank.");
        }
        questions.add(question);
    }

    public boolean updateQuestion(int id, Question update) {
        if (update == null) {
            throw new IllegalArgumentException("Question cannot be null.");
        }
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId() == id) {
                update.setId(id);
                questions.set(i, update);
                return true;
            }
        }
        return false;
    }

    public boolean removeQuetion(int id) {
        return questions.removeIf(q -> q.getId() == id);
    }

    public Question getQuestionById(int id) {
        for (Question q : questions) {
            if (q.getId() == id) {
                return q;
            }
        }
        return null;
    }
}

