package io.gitlab.jerrylum.iqtestapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import io.gitlab.jerrylum.iqtestapplication.API;
import io.gitlab.jerrylum.iqtestapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Intent i = getIntent();

        long duration = i.getLongExtra("duration", 0);
        int correct = i.getIntExtra("correct count", 0);

        duration /= 1000;

        //API.saveTest(duration, correct);

        ((TextView)findViewById(R.id.total_time)).setText(duration + "s");
        ((TextView)findViewById(R.id.avg_time)).setText(duration / 5.0 + "s");
        ((TextView)findViewById(R.id.correct_count)).setText(correct + "");

    }

    public void btnHome_OnClick(View view) {
        API.toPage(this, MainActivity.class);
        this.finish();
    }
}
