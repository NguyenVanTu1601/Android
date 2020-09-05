package com.example.flyfishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/** Không chạy nên thôi ko làm nữa :((
 * Coding Cafe
 *
 */
public class MainActivity extends AppCompatActivity {
    private FlyingFishView gameView;
    private Handler handler = new Handler();
    private final static long interval = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new FlyingFishView(this);
        setContentView(gameView);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameView.invalidate();
                    }
                });
            }
        },0,interval);



    }
}
