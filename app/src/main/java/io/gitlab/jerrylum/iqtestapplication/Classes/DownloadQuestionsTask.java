package io.gitlab.jerrylum.iqtestapplication.Classes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.gitlab.jerrylum.iqtestapplication.API;
import io.gitlab.jerrylum.iqtestapplication.UI.MainActivity;

public class DownloadQuestionsTask extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... values) {
        InputStream inputStream = null;
        String result = "";
        URL url = null;

        try {
            url = new URL("https://ajtdbwbzhh.execute-api.us-east-1.amazonaws.com/default/201920ITP4501Assignment");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // Make GET request

            con.setRequestMethod("GET");
            con.connect();

            inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((inputStream)));

            String line = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            Log.d("ApiLog", "fetch finished");
            inputStream.close();
        } catch(Exception e) {
            Log.d("ApiLog", e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            List<Question> CloudQuestions = new ArrayList<Question>();

            JSONObject jObj = new JSONObject(result);
            JSONArray campuses = jObj.getJSONArray("questions");

            //items = new String[campuses.length()];
            for (int i = 0; i < campuses.length(); i++) {
                JSONObject obj = campuses.getJSONObject(i);
                CloudQuestions.add(new Question(i, obj.getString("question"), obj.getString("answer"), false));
            }

            API.CloudQuestions = CloudQuestions;

            MainActivity.Self.onQuestionReady();

        } catch (Exception e) {
            String error = e.getMessage();
            Log.d("jsonexception", e.getMessage());
        }

    }
}