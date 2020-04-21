package com.example.gesture_cham_man_hinh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Sử dụng ges để phân biên xem cử chỉ chạm ấy thuộc loại nào
 * Sử dụn setOnTouchListener để bắt sự kiện chạm
 */
public class MainActivity extends AppCompatActivity {
    ImageView imgHinh;
    TextView txt;
    GestureDetector gestureDetector; // Nhận biết các cử chỉ vs cham, chạm lâu....
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgHinh = findViewById(R.id.imageViewHinh);
        txt = findViewById(R.id.textView);
        imgHinh.setOnTouchListener(new View.OnTouchListener() { // bắt sự kiện chạm
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        /*Cách 1 :
        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        */

        // Cách 2 :
        gestureDetector = new GestureDetector(this, new myGusture());
    }

    class myGusture extends GestureDetector.SimpleOnGestureListener{
        // Các cử chỉ : Vừa chạm xuống lần đầu là down, kéo là move, vuốt lên là up
        @Override
        public boolean onDown(MotionEvent e) { // Chạm lần đầu
            txt.setText("On Down " + e.toString());
            return super.onDown(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) { // Chạm lâu hơn 1 chút
            txt.setText("SingleTapUp " + e.toString() );
            return super.onSingleTapUp(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {      // Chạm lâu nữa
            txt.setText("showPress " + e.toString());
            super.onShowPress(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {      // Chạm lâu nữa nữa :))
            txt.setText("Long press " + e.toString());
            super.onLongPress(e);
        }
    }
}
