package com.examora.service;

import com.examora.model.*;

import java.util.ArrayList;
import java.util.List;

public class ExaminationSystem {
    private List<Student> students = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<QuestionBank> questionBanks = new ArrayList<>();
    private List<Exam> exams = new ArrayList<>();
    private List<ExamAttempt> examAttempts = new ArrayList<>();

    // Temporary id for attempts (a database would generate it)
    private int nextAttemptId = 1;

    // Adds the student; false if null or the id is already used
    public boolean registerStudent(Student student) {
        if (student == null || findStudent(student.getId()) != null)
            return false;
        students.add(student);
        return true;
    }

    // Adds the doctor; false if null or the id is already used
    public boolean registerDoctor(Doctor doctor) {
        if (doctor == null || findDoctor(doctor.getId()) != null)
            return false;
        doctors.add(doctor);
        return true;
    }

    // Adds the course; false if null or the id is already used
    public boolean addCourse(Course course) {
        if (course == null || findCourse(course.getId()) != null)
            return false;
        courses.add(course);
        return true;
    }

    // Enrolls the student in the course; false if either is missing or already enrolled
    public boolean enrollStudent(int studentId, int courseId) {
        Student student = findStudent(studentId);
        Course course = findCourse(courseId);
        if (student == null || course == null || isEnrolled(student, courseId))
            return false;
        student.getCourses().add(course);
        return true;
    }

    // Adds the exam and picks its random questions (throws if the bank has too few questions)
    public boolean addExam(Exam exam) {
        if (exam == null || findExam(exam.getId()) != null)
            return false;
        exam.getRandomQuestions();
        if (!questionBanks.contains(exam.getQuestionBank()))
            questionBanks.add(exam.getQuestionBank());
        exams.add(exam);
        return true;
    }

    // Exams of this course that are open right now
    public List<Exam> getAvailableExams(int courseId) {
        List<Exam> result = new ArrayList<>();
        for (Exam e : exams) {
            Course course = e.getQuestionBank().getCourse();
            if (course != null && course.getId() == courseId && e.isOpen())
                result.add(e);
        }
        return result;
    }

    // Starts a new attempt (the student's clock starts here)
    public ExamAttempt startExam(int studentId, int examId) {
        Student student = findStudent(studentId);
        if (student == null)
            throw new IllegalArgumentException("Student not found: " + studentId);

        Exam exam = findExam(examId);
        if (exam == null)
            throw new IllegalArgumentException("Exam not found: " + examId);

        if (!exam.isOpen())
            throw new IllegalStateException("Exam is not open.");

        Course course = exam.getQuestionBank().getCourse();
        if (course == null || !isEnrolled(student, course.getId()))
            throw new IllegalStateException("Student is not enrolled in this course.");

        if (findAttempt(studentId, examId) != null)
            throw new IllegalStateException("Student already attempted this exam.");

        ExamAttempt attempt = new ExamAttempt(nextAttemptId++, student, exam);
        examAttempts.add(attempt);
        student.getExamAttempts().add(attempt);
        return attempt;
    }

    // Adds the given answers (may be null), then closes and grades the attempt.
    // The GUI should call attempt.addAnswer(...) after every question and pass null here.
    public boolean submitAttempt(int attemptId, List<StudentAnswer> answers) {
        ExamAttempt attempt = findAttemptById(attemptId);
        if (attempt == null || attempt.isSubmitted())
            return false;

        if (answers != null) {
            for (StudentAnswer a : answers)
                attempt.addAnswer(a);
        }
        attempt.submit();
        return true;
    }

    // The student's attempt of this exam, or null if he never started it
    public ExamAttempt getStudentResult(int studentId, int examId) {
        return findAttempt(studentId, examId);
    }

    // ---------- helpers ----------

    private boolean isEnrolled(Student student, int courseId) {
        for (Course c : student.getCourses()) {
            if (c.getId() == courseId)
                return true;
        }
        return false;
    }

    private Student findStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id)
                return s;
        }
        return null;
    }

    private Doctor findDoctor(int id) {
        for (Doctor d : doctors) {
            if (d.getId() == id)
                return d;
        }
        return null;
    }

    private Course findCourse(int id) {
        for (Course c : courses) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    private Exam findExam(int id) {
        for (Exam e : exams) {
            if (e.getId() == id)
                return e;
        }
        return null;
    }

    private ExamAttempt findAttemptById(int id) {
        for (ExamAttempt a : examAttempts) {
            if (a.getId() == id)
                return a;
        }
        return null;
    }

    private ExamAttempt findAttempt(int studentId, int examId) {
        for (ExamAttempt a : examAttempts) {
            if (a.getStudent().getId() == studentId && a.getExam().getId() == examId)
                return a;
        }
        return null;
    }

    // ---------- getters / setters ----------

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<QuestionBank> getQuestionBanks() {
        return questionBanks;
    }

    public void setQuestionBanks(List<QuestionBank> questionBanks) {
        this.questionBanks = questionBanks;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public List<ExamAttempt> getExamAttempts() {
        return examAttempts;
    }

    public void setExamAttempts(List<ExamAttempt> examAttempts) {
        this.examAttempts = examAttempts;
    }
}