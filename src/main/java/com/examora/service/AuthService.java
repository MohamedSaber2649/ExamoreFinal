package com.examora.service;

import com.examora.model.Doctor;
import com.examora.model.Student;

import java.util.ArrayList;
import java.util.List;

public class AuthService {

    private final List<Student> students = new ArrayList<>();
    private final List<Doctor> doctors = new ArrayList<>();

    // Saves the student; false if null or the email is already used
    public boolean registerStudent(Student student) {
        if (student == null)
            return false;

        for (Student s : students) {
            if (s.getEmail().equals(student.getEmail()))
                return false;
        }

        students.add(student);
        return true;
    }

    // Saves the doctor; false if null or the email is already used
    public boolean registerDoctor(Doctor doctor) {
        if (doctor == null)
            return false;

        for (Doctor d : doctors) {
            if (d.getEmail().equals(doctor.getEmail()))
                return false;
        }

        doctors.add(doctor);
        return true;
    }

    // Returns the student who owns this email, or null if there is none
    public Student loginStudent(String email) {
        if (email == null)
            return null;

        for (Student student : students) {
            if (student.getEmail().equals(email.trim()))
                return student;
        }

        return null;
    }

    // Returns the doctor who owns this email, or null if there is none
    public Doctor loginDoctor(String email) {
        if (email == null)
            return null;

        for (Doctor doctor : doctors) {
            if (doctor.getEmail().equals(email.trim()))
                return doctor;
        }

        return null;
    }
}