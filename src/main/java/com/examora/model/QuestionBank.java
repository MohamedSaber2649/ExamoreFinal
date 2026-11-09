package com.examora.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestionBank {
    private String id;
    private String title;
    private Doctor doctor;
    private Course course;
    private List<Question> questions = new ArrayList<>();

    public QuestionBank( String title, Course course, Doctor doctor) {
        setId(UUID.randomUUID().toString());
        setTitle(title);
        setCourse(course);
        setDoctor(doctor);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if(doctor==null) throw  new IllegalArgumentException ("Invalid Reference");
        this.doctor = doctor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if(course==null) throw  new IllegalArgumentException ("Invalid Reference");
        this.course = course;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null.");
        }

        if (questions.equals(question)) {
            throw new IllegalArgumentException("Question already exists in this bank.");
        }
        questions.add(question);
    }

    public boolean updateQuestion(String id, Question update) {
        if (update == null) {
            throw new IllegalArgumentException("Question cannot be null.");
        }
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId().equals(id)) {
                update.setId(id);
                questions.set(i, update);
                return true;
            }
        }
        return false;
    }

    public boolean removeQuetion(String id) {
        return questions.removeIf(q -> q.getId() == id);
    }

    public Question getQuestionById(String id) {
        for (Question q : questions) {
            if (q.getId().equals(id)) {
                return q;
            }
        }
        return null;
    }
}

