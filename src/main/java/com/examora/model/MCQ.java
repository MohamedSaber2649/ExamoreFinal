package com.examora.model;

import java.util.ArrayList;
import java.util.List;

public class MCQ extends Question {
    private List<Option> options = new ArrayList<>();
    private boolean allowMultipleCorrect;

    public MCQ(String header, double mark, boolean allowMultipleCorrect) {
        super(header, mark);
        this.allowMultipleCorrect = allowMultipleCorrect;
    }

    // addOption in Option
    public void addOption(Option option) {
        if (option == null) throw new IllegalArgumentException("Option cannot be null.");
        if (options.equals(option)) throw new IllegalArgumentException("Option already exists.");
        options.add(option);
    }

    // extra if doctor want to remove the option is written unCorrect  [✓ صح] [🗑]
    public boolean removeOption(String id) {
        return options.removeIf(o -> o.getId().equals(id));
    }

    @Override
    public boolean checkAnswer(Object answer) {
        // Is answer list ? yes or no
        if (!(answer instanceof List<?>) || ((List<?>) answer).isEmpty())
            return false;
        // if is list ==> create list for it to check if Option or not
        List<?> selected = (List<?>) answer;
        // validation if student choose multiple answer but multiple answer feature is not open

        /*
        in case allow false ==> radio button
        in case allow true ==> check box
        */

        if (!allowMultipleCorrect && selected.size() != 1)// 0 2 3 4 5 6
            return false;

        for (Option o : options) {
            boolean chosen = false;
            for (Object item : selected) {

                if (item instanceof Option) {
                    Option s = (Option) item;
                    if (s.getId() .equals(o.getId())) {
                        return true;
                    }
                }
            }
            if (chosen != o.isCorrect())
                return false;
        }
        return true;
    }

    @Override
    public void display() {
        System.out.println(getHeader()+" Mark : "+getMark());
        for (int i = 0; i < options.size(); i++)
            System.out.println("   " + (i + 1) + ") " + options.get(i).getText());
        if (allowMultipleCorrect)
            System.out.println("   (Select all correct answers)");
    }
}
