package io.gitlab.jerrylum.iqtestapplication;

import androidx.appcompat.app.AppCompatActivity;
import io.gitlab.jerrylum.iqtestapplication.OO.Question;
import io.gitlab.jerrylum.iqtestapplication.Timer.AndroidStopwatchTimer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TestActivity extends AppCompatActivity {

    // Level 1 variable

    TextView tvQuestionNo;
    TextView tvQuestionStr;
    RadioGroup rgAnswerGroup;
    RadioButton rbAnswerA;
    RadioButton rbAnswerB;
    RadioButton rbAnswerC;
    RadioButton rbAnswerD;
    TextView tvTips;
    Button btnNext;
    TextView tvTimeSpan;

    RadioButton[] rbButtons;

    // Level 2 variable

    List<Question> asks;
    int asking = 0; // 0 = init, 1 ~ 5 = asking question no; index + 1
    Question askingQuestion;
    RadioButton correctBtn;

    AndroidStopwatchTimer ast;

    int correctCount = 0;

    // Level 3 variable



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // ui init

        tvQuestionNo = findViewById(R.id.q_no);
        tvQuestionStr = findViewById(R.id.question);
        rgAnswerGroup = findViewById(R.id.answer_group);
        rbAnswerA = findViewById(R.id.answer_a);
        rbAnswerB = findViewById(R.id.answer_b);
        rbAnswerC = findViewById(R.id.answer_c);
        rbAnswerD = findViewById(R.id.answer_d);
        tvTips = findViewById(R.id.tips);
        btnNext = findViewById(R.id.btn_next);
        tvTimeSpan = findViewById(R.id.time_span);

        rbButtons = new RadioButton[]{rbAnswerA, rbAnswerB, rbAnswerC, rbAnswerD};

        rgAnswerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onAnswerSelected(findViewById(checkedId));
            }
        });

        ast = new AndroidStopwatchTimer();

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                tvTimeSpan.setText("Time: " + (ast.getDisplayTicks() / 1000) + "s");
            }
        },0,100);

        // data init

        _init_restoreQuestion();

        if (asks.size() == 0)
            ast.set(0);

        _init_randomQuestion();


        // debug

        Log.d("ApiLog", "Random 5 questions:");
        for (Question q : asks) {
            Log.d("ApiLog", q.no + "," + q.question + "," + q.answer + "," + q.isCorrect);
        }

        startGame();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ast.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ast.stop();
    }

    private void _init_restoreQuestion() {
        asks = API.getAllAskedQuestion();
        int was_asking_q_no = API.getConfig().getInt("asking no", -1);

        asking = asks.size(); // important

        if (was_asking_q_no != -1)
            asks.add(API.getCloudQuestionById(was_asking_q_no));
    }

    private void _init_randomQuestion() {
        Random rand = new Random();

        while (asks.size() < 5) {
            Question target = API.CloudQuestions.get(rand.nextInt(API.CloudQuestions.size()));

            if (asks.indexOf(target) == -1)
                asks.add(target);
        }

    }

    public void onAnswerSelected(View view) {
        RadioButton rb = (RadioButton)view;

        API.setClickableAll(rgAnswerGroup, false);

        if (correctBtn == rb) {
            tvTips.setText("You are correct");
            correctCount++;
        } else {
            tvTips.setText("You are incorrect");
        }

        API.saveAskedQuestion(new Question(
                askingQuestion.no,
                askingQuestion.question,
                rb.getText() + "",
                correctBtn == rb
        ));
        API.setConfig().remove("asking no").commit();

        btnNext.setVisibility(View.VISIBLE);
    }

    public void btnNext_OnClick(View view) {
        askNextQuestion();
    }

    public void startGame() {
        // Maybe ok
        ast.start();
        askNextQuestion();
    }

    public void askNextQuestion() {
        asking++;
        if (asking > asks.size()) { // such as: 6 > 5
            endGame();
            return;
        }
        if (asking == asks.size()) btnNext.setText("Finish");

        Random rand = new Random();

        int listIndex = asking - 1;
        Question q = askingQuestion = asks.get(listIndex);

        int correctAnswer = Integer.valueOf(q.answer);

        int correctIndex = rand.nextInt(4);

        List<Integer> all_ans = new ArrayList<>();
        all_ans.add(correctAnswer);


        for (int i = 0; i < rbButtons.length; i++) {
            RadioButton rbTarget = rbButtons[i];
            if (i == correctIndex) {
                correctBtn = rbTarget;
                rbTarget.setText(correctAnswer + "");
            } else {
                int wrongAnswer = correctAnswer;
                int min = correctAnswer - 5;
                int max = correctAnswer + 5;

                while (all_ans.indexOf(wrongAnswer) != -1)
                    wrongAnswer = rand.nextInt((max - min) + 1) + min;

                all_ans.add(wrongAnswer);
                rbTarget.setText(wrongAnswer + "");
            }

            rbTarget.setChecked(false);
        }

        API.setClickableAll(rgAnswerGroup, true);

        tvQuestionNo.setText("Q" + asking);
        tvQuestionStr.setText(q.question);
        tvTips.setText("");
        btnNext.setVisibility(View.INVISIBLE);

        API.setConfig().putInt("asking no", askingQuestion.no).commit();
    }

    public void endGame() {
        // TODO
        ast.stop();
        API.deleteAllAskedQuestions();

        Intent i = new Intent(this, FinishActivity.class);
        i.putExtra("duration", ast.getDisplayTicks());
        i.putExtra("correct count", correctCount);
        //this.startActivityForResult(i, 0);
        this.startActivity(i);
        this.finish();
    }
}
