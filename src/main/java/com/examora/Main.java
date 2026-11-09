package com.examora;

import com.examora.model.Course;
import com.examora.model.Doctor;
import com.examora.model.Exam;
import com.examora.model.ExamAttempt;
import com.examora.model.MCQ;
import com.examora.model.Option;
import com.examora.model.QuestionBank;
import com.examora.model.Student;
import com.examora.model.StudentAnswer;
import com.examora.model.TrueFalseQuestion;
import com.examora.service.ExaminationSystem;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ExaminationSystem system = new ExaminationSystem();

        // ---------- 1) Course, doctor and students ----------
        // IDs are set by hand for now (the database will generate them later)
        Course course = new Course(1, "Databases");
        Doctor doctor = new Doctor(1, "Dr. Ahmed", "ahmed@mail.com", course);
        Student marwan = new Student(1, "Marwan", "marwan@mail.com");
        Student sara = new Student(2, "Sara", "sara@mail.com");
        Student ali = new Student(3, "Ali", "ali@mail.com");

        system.addCourse(course);
        system.registerDoctor(doctor);
        system.registerStudent(marwan);
        system.registerStudent(sara);
        system.registerStudent(ali);

        system.enrollStudent(1, 1);   // Marwan
        system.enrollStudent(2, 1);   // Sara
        // Ali is registered but NOT enrolled in the course

        System.out.println("Students: " + system.getStudents().size()
                + " | Courses: " + system.getCourses().size()
                + " | Marwan's courses: " + marwan.getCourses().size());

        // ---------- 2) The doctor builds a question bank ----------
        QuestionBank bank = doctor.createQuestionBank("Midterm bank");

        MCQ mcq = new MCQ(1, "Which keyword reads data from a table?", 5, false);
        Option select = new Option(1, "SELECT", true);
        Option delete = new Option(2, "DELETE", false);
        Option drop = new Option(3, "DROP", false);
        mcq.addOption(select);
        mcq.addOption(delete);
        mcq.addOption(drop);

        TrueFalseQuestion tf = new TrueFalseQuestion(2, "PostgreSQL is a relational database.", 5, true);

        bank.addQuestion(mcq);
        bank.addQuestion(tf);
        System.out.println("Bank '" + bank.getTitle() + "' has " + bank.getQuestions().size() + " questions");

        // ---------- 3) Create an exam from the bank ----------
        LocalDateTime now = LocalDateTime.now();
        Exam exam = new Exam(1, "Databases Midterm", bank,
                now.minusHours(1), now.plusHours(1),   // open from 1 hour ago until 1 hour from now
                30, 10, 2);                            // 30 minutes, 10 marks, 2 questions
        system.addExam(exam);
        System.out.println("Available exams for course 1: " + system.getAvailableExams(1).size());

        // ---------- 4) Marwan takes the exam on time ----------
        System.out.println("\n--- Marwan ---");
        ExamAttempt marwanAttempt = system.startExam(1, 1);
        exam.displayExam();

        // Save each answer as soon as the student answers it
        StudentAnswer a1 = new StudentAnswer(mcq);
        a1.setSelectedOptions(List.of(select));        // correct
        StudentAnswer a2 = new StudentAnswer(tf);
        a2.setTrueFalseAnswer(false);                  // wrong
        marwanAttempt.addAnswer(a1);
        marwanAttempt.addAnswer(a2);

        system.submitAttempt(marwanAttempt.getId(), null);
        System.out.println("Marwan's result: " + system.getStudentResult(1, 1).getResult()
                + " / " + exam.getTotalMark());

        // ---------- 5) Sara runs out of time ----------
        System.out.println("\n--- Sara ---");
        ExamAttempt saraAttempt = system.startExam(2, 1);

        StudentAnswer b1 = new StudentAnswer(tf);
        b1.setTrueFalseAnswer(true);                   // correct, saved in time
        saraAttempt.addAnswer(b1);

        // Simulate that she started 31 minutes ago (the exam lasts 30)
        saraAttempt.setStartTime(LocalDateTime.now().minusMinutes(31));
        System.out.println("Time over? " + saraAttempt.isTimeOver());

        StudentAnswer b2 = new StudentAnswer(mcq);
        b2.setSelectedOptions(List.of(select));        // too late
        try {
            saraAttempt.addAnswer(b2);
        } catch (IllegalStateException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
        System.out.println("Submitted automatically? " + saraAttempt.isSubmitted());
        System.out.println("Sara's result: " + system.getStudentResult(2, 1).getResult()
                + " / " + exam.getTotalMark());

        // ---------- 6) Things that must be refused ----------
        System.out.println("\n--- Errors ---");
        tryStart(system, 1, 1);   // Marwan already attempted this exam
        tryStart(system, 3, 1);   // Ali is not enrolled
        tryStart(system, 99, 1);  // student doesn't exist
    }

    // Tries to start an exam and prints the reason if it's refused
    private static void tryStart(ExaminationSystem system, int studentId, int examId) {
        try {
            system.startExam(studentId, examId);
            System.out.println("Started (student " + studentId + ")");
        } catch (RuntimeException e) {
            System.out.println("Student " + studentId + " refused: " + e.getMessage());
        }
    }
}