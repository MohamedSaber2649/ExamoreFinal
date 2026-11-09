package com.examora.model;

import java.util.Objects;
import java.util.UUID;

public class Option {
    private String id;
    private String text;
    private boolean correct;

    // for selection
    public Option(String id, String text, boolean correct) {
        setId(id);
        setText(text);
        this.correct = correct;
    }

    // for insert in database
    public Option(String text, boolean correct) {
        this(UUID.randomUUID().toString(),text,correct);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Option option)) return false;
        return   Objects.equals(text.toLowerCase(), option.text.toLowerCase());
    }

}
