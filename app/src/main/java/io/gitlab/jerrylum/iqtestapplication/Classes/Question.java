package io.gitlab.jerrylum.iqtestapplication.Classes;

public class Question implements Cloneable {

    public Integer no; // Q1, Q2, Q3
    public Integer idx; // the index in the question library
    public String question;
    public String answer;
    public boolean isCorrect;

    public Question(int no, int idx, String question, String answer, boolean isCorrect) {
        this.no = no;
        this.idx = idx;
        this.question = question;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public Question clone() {
        try {
            return (Question) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}
