package com.examora.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExamAttempt {
    private String id;
    private Student student;
    private Exam exam;
    private List<StudentAnswer> studentAnswers = new ArrayList<>();
    private LocalDateTime startTime;
    private LocalDateTime submissionTime; // null until submitted
    private double totalMark;
    private boolean isSubmitted;


    // Loading an existing attempt from Database
    public ExamAttempt(String Id, Student student, Exam exam, LocalDateTime startTime) {
        setId(Id);
        setStudent(student);
        setExam(exam);
        setStartTime(startTime);
    }

    public ExamAttempt(Student student, Exam exam){
       this(UUID.randomUUID().toString(),student,exam,LocalDateTime.now());
    }


    // True when the student's time is over: the duration has passed OR the exam's end date has passed
    public boolean isTimeOver() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime.plusMinutes(exam.getDuration())) || now.isAfter(exam.getEndDate());
    }

    // Closes the attempt and grades it (does nothing if it's already submitted)
    public void submit() {
        if (isSubmitted)
            return;
        calculateTotalMark();
        submissionTime = LocalDateTime.now();
        isSubmitted = true;
    }

    // Saves the answer (replaces the old one if this question was already answered)
    public void addAnswer(StudentAnswer answer) {
        if (answer == null)
            throw new IllegalArgumentException("Answer is required.");
        if (isSubmitted)
            throw new IllegalStateException("Attempt already submitted.");

        // Time is up: close the exam right now and reject this answer
        if (isTimeOver()) {
            submit();
            throw new IllegalStateException("Time is over. The exam was submitted automatically.");
        }
        // to check if question is belong to the exam or not
        boolean questionExists = false;

        for (Question q : exam.getQuestions()) {
            if (q.getId() == answer.getQuestion().getId()) {
                questionExists = true;
                break;
            }
        }

        if (!questionExists)
            throw new IllegalArgumentException("Question does not belong to this exam.");

        for (int i = 0; i < studentAnswers.size(); i++) {
            if (studentAnswers.get(i).getQuestion().getId() == answer.getQuestion().getId()) {
                studentAnswers.set(i, answer);
                return;
            }
        }
        studentAnswers.add(answer);
    }


    // Sum of the marks of all answers (unanswered questions have no answer, so they add 0)
    public double calculateTotalMark() {
        double sum = 0;
        for (StudentAnswer a : studentAnswers) {
            sum += a.calculateMark();
        }
        totalMark = sum;
        return totalMark;
    }

    // Final result, only available after the attempt is submitted
    public double getResult() {
        if (!isSubmitted)
            throw new IllegalStateException("Result is not available until the exam is submitted.");
        return totalMark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        if (student == null)
            throw new IllegalArgumentException("Student is required.");
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        if (exam == null)
            throw new IllegalArgumentException("Exam is required.");
        this.exam = exam;
    }

    public List<StudentAnswer> getStudentAnswers() {
        return studentAnswers;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (startTime == null)
            throw new IllegalArgumentException("Start time is required.");
        this.startTime = startTime;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        if (submissionTime != null && submissionTime.isBefore(startTime))
            throw new IllegalArgumentException("Submission time cannot be before start time.");
        this.submissionTime = submissionTime;
    }

    public double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(double totalMark) {
        if (totalMark < 0)
            throw new IllegalArgumentException("Total mark cannot be negative.");
        this.totalMark = totalMark;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }


}