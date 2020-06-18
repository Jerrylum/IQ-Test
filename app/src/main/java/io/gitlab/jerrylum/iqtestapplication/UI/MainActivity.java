package io.gitlab.jerrylum.iqtestapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import io.gitlab.jerrylum.iqtestapplication.API;
import io.gitlab.jerrylum.iqtestapplication.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static MainActivity Self;

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Self = this; // important

        btnStart = findViewById(R.id.btn_start);

        API.initDatabase();
        API.fetchCloudQuestion();

        btnStart.setEnabled(API.CloudQuestions != null); // important

        if (API.getAllAskedQuestion().size() != 0 || API.getConfig().getInt("asking no", -1) != -1)
            btnStart.setText("Continue");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        AllMenu.createOptionsMenu(this, R.menu.main_menu, menu);
        return true;
    }

    public void onQuestionReady() {
        btnStart.setEnabled(true);
    }

    public void start_OnClick(View view) {
        view.setEnabled(false); // important
        API.toPage(this, TestActivity.class);
        this.finish();
    }
}
