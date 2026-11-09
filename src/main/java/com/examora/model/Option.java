package com.examora.model;

public class Option {
    private int id;
    private String text;
    private boolean correct;

    public Option(int id, String text, boolean correct) {
        setId(id);
        setText(text);
        this.correct = correct;
    }

    public Option(String text, boolean correct) {
        this(0, text, correct);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be negative.");
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null || text.isBlank())
            throw new IllegalArgumentException("Option text is required.");
        this.text = text.trim();
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "Option { id : " + id + ", text :'" + text + "'}";
    }

}
