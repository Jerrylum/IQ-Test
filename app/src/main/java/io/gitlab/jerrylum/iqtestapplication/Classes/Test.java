package io.gitlab.jerrylum.iqtestapplication.Classes;

public class Test {
    public Integer id;
    public String date;
    public String time;
    public int duration;
    public int correctCount;

    public Test(int id, String date_str, String time_str, int duration, int correctCount) {
        this.id = id;
        this.date = date_str;
        this.time = time_str;
        this.duration = duration;
        this.correctCount = correctCount;
    }
}
