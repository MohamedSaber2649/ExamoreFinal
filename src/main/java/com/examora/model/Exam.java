package com.examora.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exam {
    private int id;
    private String title;
    private QuestionBank questionBank;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int duration;  // minutes
    private double totalMark;
    private int numberOfQuestions;
    private List<Question> questions = new ArrayList<>();

    public Exam(int id, String title, QuestionBank questionBank, LocalDateTime startDate, LocalDateTime endDate, int duration, double totalMark, int numberOfQuestions) {
        setId(id);
        setTitle(title);
        setQuestionBank(questionBank);
        setStartDate(startDate);
        setEndDate(endDate);
        setDuration(duration);
        setTotalMark(totalMark);
        setNumberOfQuestions(numberOfQuestions);
    }

    public Exam(String title, QuestionBank questionBank, LocalDateTime startDate, LocalDateTime endDate, int duration, double totalMark, int numberOfQuestions) {
        this(0, title, questionBank, startDate, endDate, duration, totalMark, numberOfQuestions);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("ID cannot be negative.");
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Exam title is required.");
        this.title = title.trim();
    }

    public QuestionBank getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(QuestionBank bank) {
        if (bank == null) throw new IllegalArgumentException("Question bank is required.");
        this.questionBank = bank;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime date) {
        if (date == null) throw new IllegalArgumentException("Start date is required.");
        if (endDate != null && date.isAfter(endDate))
            throw new IllegalArgumentException("Start date must be before end date.");
        this.startDate = date;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime date) {
        if (date == null) throw new IllegalArgumentException("End date is required.");
        // check the start date
        if (startDate != null && date.isBefore(startDate))
            throw new IllegalArgumentException("End date must be after start date.");
        this.endDate = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration <= 0)
            throw new IllegalArgumentException("Duration must be positive.");
        this.duration = duration;
    }

    public double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(double mark) {
        if (mark <= 0)
            throw new IllegalArgumentException("Total mark must be positive.");
        this.totalMark = mark;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int number) {
        if (number <= 0)
            throw new IllegalArgumentException("Number of questions must be positive.");
        // The exam can't be set to fewer questions than it already contains
        //        exam.getRandomQuestions();      دلوقتي فيه 5 أسئلة فعلاً
        //        exam.setNumberOfQuestions(3);    ❌ exception
        if (number < questions.size())
            throw new IllegalArgumentException("Cannot be less than the current number of questions.");
        this.numberOfQuestions = number;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    // Exam is open when now is between startDate and endDate (inclusive).
    public boolean isOpen() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(startDate) && !now.isAfter(endDate);
    }

    public List<Question> getRandomQuestions() {
        // Copy the bank's questions so shuffling doesn't change the original bank
        List<Question> all = new ArrayList<>(questionBank.getQuestions());
        // Stop early if the bank can't provide enough questions (before touching the exam's questions)
        if (all.size() < numberOfQuestions)
            throw new IllegalStateException("Question bank has only " + all.size() +
                    " questions, but the exam needs " + numberOfQuestions + ".");
        Collections.shuffle(all);
        // Remove the previous selection so each call replaces the questions instead of adding to them.
        questions.clear();
        for (int i = 0; i < numberOfQuestions; i++) {
            // Add the question at position i from the shuffled list to the exam
            questions.add(all.get(i));
        }
        return questions;
    }

    public void displayExam() {
        System.out.println("=== " + title + " ===");
        System.out.println("Duration: " + duration + " min | Total mark: " + totalMark + " | Questions: " + numberOfQuestions);
        System.out.println("From " + startDate + " to " + endDate + " | " + (isOpen() ? "OPEN" : "CLOSED"));
        System.out.println();
        if (questions.isEmpty()) {
            System.out.println("(No questions selected yet)");
            return;
        }
        for (Question q : questions) {
            q.display();
        }
    }
}
