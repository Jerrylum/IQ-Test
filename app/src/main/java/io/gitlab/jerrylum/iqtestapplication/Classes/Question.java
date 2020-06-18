package io.gitlab.jerrylum.iqtestapplication.Classes;

public class Question {

    public Integer no;
    public String question;
    public String answer;
    public boolean isCorrect;

    public Question(int no, String question, String answer, boolean isCorrect) {
        this.no = no;
        this.question = question;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
}
