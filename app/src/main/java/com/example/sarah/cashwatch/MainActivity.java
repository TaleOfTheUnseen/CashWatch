package com.example.sarah.cashwatch;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    private EditText editSalary;
    private TextView counterTextView;
    private double hourlySalary = 0.0;
    private double secondSalary;
    private double earnedMoney = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSalary = findViewById(R.id.editSalay);
        counterTextView = findViewById(R.id.counterTextView);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                earnedMoney += secondSalary;
                counterTextView.setText(String.format("$ %f", earnedMoney));
            }
        });
    }

    public void startChronometer(View v) {
        if(editSalary.getText().equals("")){
            Toast.makeText(this, "Please enter a salary! :)", Toast.LENGTH_SHORT).show();
            return;
        }
        hourlySalary = Double.valueOf(editSalary.getText().toString());
        secondSalary = hourlySalary / 3600;
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        earnedMoney = 0.0;
        counterTextView.setText(String.format("$ %f", earnedMoney));
    }

    public void moneyMode(View view) {
    }

    public void pintMode(View view) {
    }
}