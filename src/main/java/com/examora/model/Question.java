package com.examora.model;

public abstract class Question {
    private int id;
    private String header;
    private double mark;

    public Question(int id, String header, double mark) { // when I select from the database
        setId(id);
        setHeader(header);
        setMark(mark);
    }

    public Question(String header, double mark) { // when iam insert in database
        this(0, header, mark);
    }

    public abstract boolean checkAnswer(Object answer);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be negative.");
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        if (header == null || header.isBlank())
            throw new IllegalArgumentException("Question text is required.");
        this.header = header.trim();
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        if (mark < 0)
            throw new IllegalArgumentException("Mark cannot be negative or null.");
        this.mark = mark;
    }

    public void display() {
        System.out.println("Q" + id + ": " + header + " (" + mark + " marks)");
    }

    // the display and toString is Same
    @Override
    public String toString() {
        return "Question { id : " + id + ", header : '" + header + "', mark : " + mark + "}";
    }
}
