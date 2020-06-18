package io.gitlab.jerrylum.iqtestapplication.UI;

import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import io.gitlab.jerrylum.iqtestapplication.API;
import io.gitlab.jerrylum.iqtestapplication.R;

public class AllMenu {

    public static void createOptionsMenu(final AppCompatActivity a, int id, Menu menu) {
        MenuInflater i = a.getMenuInflater();
        i.inflate(id, menu);

        menu.findItem(R.id.qlog).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                API.toPage(a, QuestionsLogActivity.class);
                return true;
            }
        });
        menu.findItem(R.id.tlog).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("ApiLog", "To tests log page");
                return true;
            }
        });
    }
}
