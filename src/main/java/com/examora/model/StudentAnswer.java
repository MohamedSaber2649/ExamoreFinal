package com.examora.model;

import java.util.ArrayList;
import java.util.List;

public class StudentAnswer {
    private int id;
    private Question question;
    private List<Option> selectedOptions = new ArrayList<>(); // used for MCQ
    private Boolean trueFalseAnswer;                          // used for True/False (null = not answered)
    private double awardedMark;

    public StudentAnswer(int id, Question question) {
        setId(id);
        setQuestion(question);
    }

    public StudentAnswer(Question question) {
        this(0, question);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be negative.");
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        if (question == null)
            throw new IllegalArgumentException("Question is required.");
        this.question = question;
    }

    public List<Option> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<Option> selectedOptions) {
        if (selectedOptions == null)
            throw new IllegalArgumentException("Selected options cannot be null.");
        this.selectedOptions = selectedOptions;
    }

    public Boolean getTrueFalseAnswer() {
        return trueFalseAnswer;
    }

    public void setTrueFalseAnswer(Boolean trueFalseAnswer) {
        this.trueFalseAnswer = trueFalseAnswer;
    }

    public double getAwardedMark() {
        return awardedMark;
    }

    public void setAwardedMark(double awardedMark) {
        if (awardedMark < 0)
            throw new IllegalArgumentException("Awarded mark cannot be negative.");
        this.awardedMark = awardedMark;
    }

    // Returns true if the student's answer is correct
    public boolean checkAnswer() {
        if (question instanceof MCQ) {
            return question.checkAnswer(selectedOptions);
        }
        if (question instanceof TrueFalseQuestion) {
            return trueFalseAnswer != null && question.checkAnswer(trueFalseAnswer);
        }
        return false;
    }

    public double calculateMark() {
        // If the answer is correct, award the question's full mark; otherwise award 0 ==> this is Ternary Operator
        awardedMark = checkAnswer() ? question.getMark() : 0;
        return awardedMark;
    }
}

