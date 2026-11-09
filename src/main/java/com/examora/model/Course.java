package com.examora.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Course {
    private String id;
    private String name;
    private final List<Doctor> doctors = new ArrayList<>();

    public Course( String name) {
        setId(UUID.randomUUID().toString());
        setName(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
