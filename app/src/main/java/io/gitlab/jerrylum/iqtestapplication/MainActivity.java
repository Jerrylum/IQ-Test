package io.gitlab.jerrylum.iqtestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static MainActivity Self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Self = this; // important

        API.initDatabase();
        API.fetchCloudQuestion();

//        API.saveTest(120, 4);
//        List<Test> tests = API.getAllTest();
//
//        Test last = tests.get(tests.size()-1);
//
//        Log.d("ApiLog", last.date + "," + last.time + "," + last.duration);

//        Question q = new Question(2, "What does the fox say?", "Wow", true);
//
//        API.saveQuestion(q);
//
//        List<Question> qs = API.getAllQuestion();
//
//        Question last = qs.get(qs.size()-1);
//
//        Log.d("ApiLog", last.no + "," + last.question + "," + last.answer + "," + last.isCorrect);

    }

    public void onQuestionReady() {

    }

    public void start_OnClick(View view) {
        API.toPage(this, TestActivity.class);
    }
}
