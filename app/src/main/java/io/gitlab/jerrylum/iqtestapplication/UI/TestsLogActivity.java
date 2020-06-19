package io.gitlab.jerrylum.iqtestapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import io.gitlab.jerrylum.iqtestapplication.API;
import io.gitlab.jerrylum.iqtestapplication.Classes.Question;
import io.gitlab.jerrylum.iqtestapplication.Classes.Test;
import io.gitlab.jerrylum.iqtestapplication.R;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

public class TestsLogActivity extends AppCompatActivity {

    LinearLayout MainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_log);

        MainLayout = findViewById(R.id.main_layout);

        List<Test> tests = API.getAllTest();

        Log.d("ApiLog", ">>>" + tests.size());

        for (Test t : tests) {
            Log.d("ApiLog", ">>" + t.id);
            TableLayout view = (TableLayout) LayoutInflater.from(this).inflate(R.layout.view_test, null);
            ((TextView)view.findViewById(R.id.tno)).setText("Test " + t.id);
            ((TextView)view.findViewById(R.id.date)).setText(t.date + "");
            ((TextView)view.findViewById(R.id.time)).setText(t.time + "");
            ((TextView)view.findViewById(R.id.duration)).setText(t.duration + "s");
            ((TextView)view.findViewById(R.id.correct)).setText(t.correctCount + "");
            MainLayout.addView(view);
        }
    }

    public void openChart_OnClick(View view) {
        API.toPage(this, TestsChartActivity.class);
    }
}
