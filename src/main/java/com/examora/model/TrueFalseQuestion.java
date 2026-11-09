package com.examora.model;

public class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    public TrueFalseQuestion(String header, double mark, boolean correctAnswer) {
        super(header, mark);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean checkAnswer(Object answer) {
        if (answer instanceof Boolean) {
            boolean value = (Boolean) answer;
            return value == correctAnswer;
        }
        //        return answer instanceof Boolean value && value == correctAnswer; ==> Pattern matching
        return false;
    }

    public boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public void display() {
        System.out.println(getHeader()+"Mark: "+ getMark());
        System.out.println("[True / False]");
    }
}
