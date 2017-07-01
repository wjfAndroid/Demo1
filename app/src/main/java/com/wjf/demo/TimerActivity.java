package com.wjf.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends Activity {
    Timer timer;
    int i = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("TimerActivity", "handleMessage   msg.arg1:" + msg.arg1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //true表示不再主线程，
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("TimerActivity", "timer currentThread " + Thread.currentThread().getName());
                Message message = Message.obtain(handler);
                message.arg1 = i;
                handler.sendMessage(message);
                i++;
                if (i == 10) {
                    cancel();
                }
            }
        }, 2000, 1000);


        if (i > 1) {
            Log.e("TimerActivity", "11111111111111 " );
        } else Log.e("TimerActivity", "2222222222222" );
        Log.e("TimerActivity", "333333333333" );
    }


}
