package com.examora.model;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {
    private static int nextBankId = 1;  // to generate the Bank id but when use database we remove this
    private List<QuestionBank> questionBanks = new ArrayList<>();
    private Course course;

    public Doctor(int id, String name, String email, Course course) {
        super(id, name, email);
        setCourse(course);
    }

    public Doctor(String name, String email, Course course) {
        super(name, email);
        setCourse(course);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if (course == null)
            throw new IllegalArgumentException("Doctor must be assigned to a course.");
        this.course = course;
    }

    public List<QuestionBank> getQuestionBanks() {
        return questionBanks;
    }

    public void setQuestionBanks(List<QuestionBank> questionBanks) {
        this.questionBanks = questionBanks;
    }

    public QuestionBank createQuestionBank(String title) {
        if (course == null) {
            throw new IllegalStateException("Doctor must choose a course before creating a question bank.");
        }
        QuestionBank bank = new QuestionBank(nextBankId++, title, course, this);
        questionBanks.add(bank);
        return bank;
    }

    public QuestionBank getBankById(int id) {
        for (QuestionBank b : questionBanks) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public void addQuestionBank(QuestionBank bank) {
        if (bank == null || getBankById(bank.getId()) != null) {
            return;
        }
        bank.setDoctor(this);
        questionBanks.add(bank);
    }

    public boolean updateQuestionBank(int id, QuestionBank bank) {
        if (bank == null) {
            throw new IllegalArgumentException("bank cannot be null.");
        }
        for (int i = 0; i < questionBanks.size(); i++) {
            if (questionBanks.get(i).getId() == id) {
                bank.setId(id);
                bank.setDoctor(this);
                questionBanks.set(i, bank);
                return true;
            }
        }
        return false;
    }

    public boolean deleteQuestionBank(int id) {
        // filter  => remove the bank if the getId == id
        return questionBanks.removeIf((b) -> b.getId() == id);
    }

    //  ملهاش لازمه نخلي عند الدكتور choose course لان بتعمل اللي setCourse بتعمله!!!

    public List<QuestionBank> viewBanks() {
        return new ArrayList<>(questionBanks); // رجعت نسخه جديده علشان تفضل النسخه اللي عند الدكتور سليمه محدش يقدر يتحكم فيها
    }

    @Override
    public String toString() {
        return "Doctor { id : " + getId() + ", name : '" + getName() + "Course : " + getCourse() + "'}";
    }
}
