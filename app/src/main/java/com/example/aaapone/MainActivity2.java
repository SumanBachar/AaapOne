package com.example.aaapone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    private TextView tvShow;
    private Button startTh,stopTh;
    private Handler mainHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /*startActivity(new Intent(MainActivity2.this,MainActivity3.class));
        finish();*/

        tvShow = findViewById(R.id.tv_show);
        startTh = findViewById(R.id.start_thread);
        stopTh = findViewById(R.id.stop_thread);
        startTh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startThread();
            }
        });
        stopTh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopThread();
            }
        });
    }

    private void startThread() {
        ExampleThread exampleThread=new ExampleThread(10);
        exampleThread.start();
        //ExampleRunnable exampleRunnable = new ExampleRunnable(10);
        //new Thread(exampleRunnable).start();
    }

    private void stopThread() {

    }

    private class ExampleThread extends Thread {
        int sec;
        ExampleThread(int sec) {
            this.sec = sec;
        }

        @Override
        public void run() {
            for (int i = 0; i < sec; i++) {
                Log.d(TAG, "started thread " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ExampleRunnable implements Runnable {
        int sec;
        ExampleRunnable(int sec) {
            this.sec = sec;
        }

        @Override
        public void run() {
            for (int i = 0; i < sec; i++) {
                if (i == 5) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            startTh.setText("50%");
                        }
                    });
                }
                Log.d(TAG, "started thread " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}