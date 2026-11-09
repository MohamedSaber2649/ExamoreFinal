package com.examora.model;
import java.util.Objects;
import java.util.UUID;

public abstract class Question {
    private String id;
    private String header;
    private double mark;

    public Question(String header, double mark) { // when I select from the database
        setId(UUID.randomUUID().toString()); // for generate ronmdom id
        setHeader(header);
        setMark(mark);
    }

    public abstract boolean checkAnswer(Object answer);

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public abstract void display() ;

    // the display and toString is Same
    @Override
    public String toString() {
        return "Question { id : " + id + ", header : '" + header + "', mark : " + mark + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Question question)) return false;
        return  Objects.equals(header.toLowerCase(), question.header.toLowerCase());
    }

}
