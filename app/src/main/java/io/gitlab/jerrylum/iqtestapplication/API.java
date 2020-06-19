package io.gitlab.jerrylum.iqtestapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import io.gitlab.jerrylum.iqtestapplication.Classes.DownloadQuestionsTask;
import io.gitlab.jerrylum.iqtestapplication.Classes.Question;
import io.gitlab.jerrylum.iqtestapplication.Classes.Test;
import io.gitlab.jerrylum.iqtestapplication.UI.MainActivity;

public class API {
    private static final String DBPATH = "/data/data/io.gitlab.jerrylum.iqtestapplication/MainDB";

    private static SQLiteDatabase db;
    private static String sql;
    private static Cursor cursor = null;
    private static DownloadQuestionsTask task;

    public static List<Question> CloudQuestions;

    public static void toPage(AppCompatActivity aca, Class<?> cls){
        Intent i = new Intent(aca, cls);
        aca.startActivity(i);
    }

    public static void initDatabase() {
        try {
            // Create a database if it does not exist
            db = SQLiteDatabase.openDatabase(DBPATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);

            // db.execSQL("DROP TABLE QuestionsLog;");
            // db.execSQL("DROP TABLE TestsLog;");

            sql =   "CREATE TABLE IF NOT EXISTS `QuestionsLog` (\n" +
                    "  `questionNo` INTEGER PRIMARY KEY,\n" +
                    "  `questionIdx` INTEGER,\n" +
                    "  `question` text,\n" +
                    "  `yourAnswer` text,\n" +
                    "  `isCorrect` ENUM (1, 0)\n" +
                    ");\n";
            db.execSQL(sql);
            sql =   "CREATE TABLE IF NOT EXISTS `TestsLog` (\n" +
                    "  `id` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  `testDate` text,\n" +
                    "  `testTime` text,\n" +
                    "  `duration` int,\n" +
                    "  `correctCount` int\n" +
                    ");\n";
            db.execSQL(sql);

            Log.d("ApiLog", "Created database");
            db.close();
        } catch (SQLiteException e) {
            Log.d("ApiLog", "Cannot created database, " + e);
        }
    }

    //

    public static boolean saveTest(long duration, int correct) {
        try {
            db = SQLiteDatabase.openDatabase(DBPATH, null, SQLiteDatabase.OPEN_READWRITE);

            db.execSQL("INSERT INTO TestsLog (`testDate`, `testTime`, `duration`, `correctCount`) " +
                            "VALUES (date('now', 'localtime'), time('now', 'localtime'), ?, ?)",
                           new Object[] {duration, correct}
                       );

            db.close();
            return true;
        } catch (SQLiteException e) {
            Log.d("ApiLog", "Cannot add test, " + e);
            return false;
        }
    }

    public static List<Test> getAllTest() {
        List<Test> tests = new ArrayList<Test>();

        try {
            db = SQLiteDatabase.openDatabase(DBPATH, null, SQLiteDatabase.OPEN_READONLY);

            cursor = db.rawQuery("select * from TestsLog order by `id` desc", null);

            while (cursor.moveToNext()) {
                tests.add(new Test(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
                ));
            }

            db.close();
        } catch (SQLiteException e) {
            Log.d("ApiLog", "Cannot get all tests, " + e);
        }

        return tests;
    }

    //

    public static List<Question> getAllAskedQuestion() {
        List<Question> q = new ArrayList<Question>();

        try {
            db = SQLiteDatabase.openDatabase(DBPATH, null, SQLiteDatabase.OPEN_READONLY);

            cursor = db.rawQuery("select * from QuestionsLog order by `questionNo` asc", null);

            while (cursor.moveToNext()) {
                q.add(new Question(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) == 1
                ));
            }

            db.close();
        } catch (SQLiteException e) {
            Log.d("ApiLog", "Cannot get all tests, " + e);
        }

        return q;
    }

    public static boolean saveAskedQuestion(Question q) {
        try {
            db = SQLiteDatabase.openDatabase(DBPATH, null, SQLiteDatabase.OPEN_READWRITE);

            db.execSQL("INSERT INTO QuestionsLog " +
                            "VALUES (?, ?, ?, ?, ?)",
                    new Object[] {q.no, q.idx, q.question, q.answer, q.isCorrect ? 1 : 0}
            );

            Log.d("ApiLog", "Add question: " + q.idx);

            db.close();
            return true;
        } catch (SQLiteException e) {
            Log.d("ApiLog", "Cannot add question, " + e);
            return false;
        }
    }

    public static boolean deleteAllAskedQuestions() {
        try {
            db = SQLiteDatabase.openDatabase(DBPATH, null, SQLiteDatabase.OPEN_READWRITE);

            db.execSQL("DELETE FROM QuestionsLog;");

            db.close();
            return true;
        } catch (SQLiteException e) {
            Log.d("ApiLog", "Cannot delete question, " + e);
            return false;
        }
    }

    //

    public static void fetchCloudQuestion() {
        if (task == null ||
                task.getStatus().equals(AsyncTask.Status.FINISHED)) {
            task = new DownloadQuestionsTask();
            task.execute();
            Log.d("ApiLog", "Start fetch question ");
        }
    }

    public static Question getCloudQuestionById(int no) {
        for (Question q : CloudQuestions)
            if (q.idx == no)
                return q;
        return null;
    }

    //

    public static SharedPreferences.Editor setConfig() {
        SharedPreferences.Editor editor = MainActivity.Self.getPreferences(0).edit();
        return editor;
    }

    public static SharedPreferences getConfig() {
        return MainActivity.Self.getPreferences(0);
    }

    //

    public static void setClickableAll(View v, boolean clickable) {
        v.setClickable(clickable);
        v.setFocusable(clickable);

        if(v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++)
                setClickableAll(vg.getChildAt(i), clickable);
        }
    }

}
