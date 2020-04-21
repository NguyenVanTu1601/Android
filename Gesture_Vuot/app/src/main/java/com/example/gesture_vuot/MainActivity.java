package com.example.gesture_vuot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int khoang_cach = 100; // nếu > thì thành công
    int van_toc = 100;
    GestureDetectorCompat mConpat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConpat = new GestureDetectorCompat(this,new myGesture());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mConpat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public class myGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e2.getX()  - e1.getX() > khoang_cach && Math.abs(velocityX) > van_toc){
                Toast.makeText(MainActivity.this, "Vuốt từ trái sang phải", Toast.LENGTH_SHORT).show();

            }

            // Vuốt phải sang trái
            if(e1.getX()  - e2.getX() > khoang_cach && Math.abs(velocityX) > van_toc){
                Toast.makeText(MainActivity.this, "Vuốt từ phải sang trái", Toast.LENGTH_SHORT).show();

            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
