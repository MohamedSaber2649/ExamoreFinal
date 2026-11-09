package com.examora.model;

public abstract class Person {
    private int id;
    private String name;
    private String email;

    public Person(int id, String name, String email) {
        setId(id);
        setName(name);
        setEmail(email);
    }

    public Person(String name, String email) {
        this(0, name, email);
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
            throw new IllegalArgumentException("Name is required.");
        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new IllegalArgumentException("A valid email is required.");
        this.email = email.trim().toLowerCase();
    }

    @Override
    public String toString() {
        return "Person { id: " + id + ", name: '" + name + "', email: '" + email + "'}";
    }
}
