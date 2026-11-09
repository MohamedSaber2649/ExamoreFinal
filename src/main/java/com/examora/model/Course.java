package com.examora.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private String name;
    private final List<Doctor> doctors = new ArrayList<>();

    public Course(int id, String name) {
        setId(id);
        setName(name);
    }

    public Course(String name) {
        this(0, name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be negative.");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Course name is required.");
        this.name = name.trim();
    }


    public List<Doctor> getDoctors() {
        return doctors;
    }

    @Override
    public String toString() {
        return "Course { id: " + id + ", name: '" + name + "'}";
    }
}
