package com.example.aaapone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private int i = 0, j = 0;
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new MyTask().execute();

        startActivity(new Intent(MainActivity.this,MainActivity4.class));
        finish();
    }

    private class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            i = 1;
        }

        @Override
        protected String doInBackground(Void... voids) {
            if (i == 1) {
                return "one";
            } else {
                return "two";
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("one")) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }

        }
    }

}