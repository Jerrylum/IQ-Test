package io.gitlab.jerrylum.iqtestapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import io.gitlab.jerrylum.iqtestapplication.API;
import io.gitlab.jerrylum.iqtestapplication.Classes.Test;
import io.gitlab.jerrylum.iqtestapplication.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class TestsChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tests_chart);
//
//        LinearLayout main = ((LinearLayout)findViewById(R.id.main_layout));
//        main.addView(new TestsChart(this));
        setContentView(new TestsChart(this));
    }

    public class TestsChart extends View {
        Paint paint = new Paint();

        List<Test> tests;

        public TestsChart(Context context) {
            super(context);
            tests = API.getAllTest();

            invalidate();
        }

        /**
         * The screen:
         *
         *                   The Result On Different Tests
         *
         *               ┃
         *             5 ┃       █
         *             4 ┃       █   █               █
         *   Correct   3 ┃   █   █   █           █   █   █
         *    Count    2 ┃   █   █   █   █       █   █   █
         *             1 ┃   █   █   █   █   █   █   █   █
         *           ━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
         *               ┃   8   7   6   5   4   3   2   1
         *               ┃             Test No.
         *
         */

        @Override
        protected void onDraw(Canvas canvas) {


            ///

            final int sw = getWidth();
            final int sh = getHeight();
            int every_start_top = 40;
            int every_start_left = 80;
            int every_end_bottom = sh - 80;
            int every_end_right = sw - 80;


            int title_font_size = 60;
            int title_margin_down = 60;
            int number_font_size = 48;
            int axis_stroke_width = 4;
            int axis_title_font_size = 52;
            int x_axis_title_margin_left = 0;
            int bar_width = 80;
            int bar_margin = 80; // left


            int chart_start_y = every_start_top + title_font_size + title_margin_down;
            int chart_end_y = every_end_bottom - axis_title_font_size - 40 - number_font_size;
            int chart_start_x = axis_title_font_size * 6 + x_axis_title_margin_left + number_font_size;
            int chart_end_x = chart_start_x + Math.max(Math.min((bar_width + bar_margin) * tests.size(), every_end_right), 1200);


            int x_axis_margin_left = chart_start_x - (every_end_bottom - chart_end_y);


            int x_axis_title_x = (chart_end_x + chart_start_x) / 2;
            int x_axis_title_y = chart_end_y + number_font_size + 40 + axis_title_font_size;
            int y_axis_title_x = (x_axis_margin_left + every_start_left) / 2;
            int y_axis_title_y = (chart_start_y + chart_end_y) / 2;

            int block_height = (chart_end_y - chart_start_y) / 6;

            ///


            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(axis_stroke_width);
            canvas.drawLine(chart_start_x, chart_start_y, chart_start_x, every_end_bottom, paint);
            canvas.drawLine(x_axis_margin_left, chart_end_y, chart_end_x, chart_end_y, paint);


            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(axis_title_font_size);
            paint.setTypeface(Typeface.create("Arial", Typeface.NORMAL));
            canvas.drawText("Test No." , x_axis_title_x, x_axis_title_y, paint);
            canvas.drawText("Correct" , y_axis_title_x, y_axis_title_y - axis_title_font_size / 2, paint);
            canvas.drawText("Count" , y_axis_title_x, y_axis_title_y + axis_title_font_size / 2, paint);


            paint.setTextSize(title_font_size);
            canvas.drawText("The Result On Different Tests" , x_axis_title_x, every_start_top + title_font_size, paint);


            paint.setTextAlign(Paint.Align.RIGHT);
            paint.setTextSize(number_font_size);
            for (int i = 1; i <= 5; i++) {
                int num_x = chart_start_x - 15;
                int num_y = chart_end_y - block_height * i + number_font_size / 2;
                canvas.drawText(i + "" , num_x, num_y, paint);
            }


            paint.setTextAlign(Paint.Align.CENTER);
            int i = 1;
            for (Test t : tests) {
                int num_x = chart_start_x + (bar_margin + bar_width) * i - bar_width / 2;
                int num_y = chart_end_y + number_font_size + 15;
                paint.setColor(Color.GRAY);
                canvas.drawText(t.id + "" , num_x, num_y, paint);

                int bar_y2 = chart_end_y - axis_stroke_width / 2;
                int bar_x1 = chart_start_x + (bar_margin + bar_width) * (i - 1) + bar_margin;
                int bar_y1 = bar_y2 - block_height * t.correctCount;

                paint.setColor(Color.MAGENTA);
                canvas.drawRect(bar_x1, bar_y1, bar_x1 + bar_width, bar_y2, paint);
                i++;
            }
        }
    }
}
