package io.gitlab.jerrylum.iqtestapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import io.gitlab.jerrylum.iqtestapplication.API;
import io.gitlab.jerrylum.iqtestapplication.Classes.Question;
import io.gitlab.jerrylum.iqtestapplication.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

public class QuestionsLogActivity extends AppCompatActivity {

    LinearLayout MainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_log);

        MainLayout = findViewById(R.id.main_layout);

        List<Question> questions = API.getAllAskedQuestion();

        for (Question q : questions) {
            TableLayout view = (TableLayout) LayoutInflater.from(this).inflate(R.layout.view_question, null);
            ((TextView)view.findViewById(R.id.qno)).setText("Q" + q.no);
            ((TextView)view.findViewById(R.id.idx)).setText(q.idx + "");
            ((TextView)view.findViewById(R.id.question)).setText(q.question + "");
            ((TextView)view.findViewById(R.id.answer)).setText(q.answer + "");
            ((TextView)view.findViewById(R.id.correct)).setText(q.isCorrect ? "Yes" : "No");
            MainLayout.addView(view);
        }
    }


}
