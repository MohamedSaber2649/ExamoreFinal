package com.examora.model;

public class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    public TrueFalseQuestion(int id, String header, double mark, boolean correctAnswer) {
        super(id, header, mark);
        this.correctAnswer = correctAnswer;
    }

    public TrueFalseQuestion(String header, double mark, boolean correctAnswer) {
        this(0, header, mark, correctAnswer);
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
        super.display();
        System.out.println("   [True / False]");
    }
}
