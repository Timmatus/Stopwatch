package com.mgke.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private int seconds = 0;  //В переменных seconds и running хранится количество прошедших секунд и флаг работы секундомера
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds"); //Состояние активности восстанавливается по значениям, прочитанным из Bundle.
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }
    public void onSaveInstanceState(Bundle savedInstanceState) {//Состояние переменных сохраняется в методе onSaveInstanceState() активности
        super.onSaveInstanceState(savedInstanceState);//Сохранить состояние приложения при работе в фоновом режиме
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
    public void onClickStart(View view) {//Вызывается при щелчке на кнопке Start
        running = true;  //Запустить секундомер
    }

    public void onClickStop(View view) {//Вызывается при щелчке на кнопке Stop
        running = false; //Остановить секундомер
    }

    public void onClickReset(View view) {//Вызывается при щелчке на кнопке Reset
        running = false;
        seconds = 0;//Остановить секундомер и присвоить счетчику секунд 0
    }
    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.textView);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wasRunning) {
            running = true;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }



}