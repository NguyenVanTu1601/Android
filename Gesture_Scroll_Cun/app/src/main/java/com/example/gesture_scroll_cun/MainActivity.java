package com.example.gesture_scroll_cun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayName;
    ArrayAdapter adapter;
    GestureDetector gestureDetector;
    int Ymin = 200;
    int index = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        initArray();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayName);
        listView.setAdapter(adapter);

        gestureDetector = new GestureDetector(this, new myGesture());

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private void initArray(){
        arrayName = new ArrayList<>();
        arrayName.add("Bích Không Long Ngâm");
        arrayName.add("Mộc Vũ Tranh Phong");
        arrayName.add("Thảo Thế Kiếm");
        arrayName.add("Bát Chí Kính");
        arrayName.add("Hoàng Mộc Ngưng Vũ");
        arrayName.add("Túc Thế Lôi Âm");
        arrayName.add("Mệnh Chùy Linh Hy");
        arrayName.add("Ngọc Giản Cửu Ca");
        arrayName.add("Loạn Hồn Tàn Tuyết");
        arrayName.add("Viêm Thương Trọng Lê");
        arrayName.add("Huyết Ảnh Lưu Tinh");

    }

    private class myGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // e1, e2 là vị trí scroll từ 1 đến 2
            // distanceX, Y ko phải khoảng cách giữa 2 điểm disX là khoảng cách từ e1 tới e2 theo chiều mang, disY là chiều dọc
            if(e2.getY() - e1.getY() > Ymin){
                Toast.makeText(MainActivity.this, "Scroll xuống", Toast.LENGTH_SHORT).show();
                arrayName.add("Tên thứ " + index);
                index ++;
                adapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(MainActivity.this, "Scroll lên", Toast.LENGTH_SHORT).show();
                if(index % 5 == 0){
                    index--;
                    arrayName.remove(arrayName.size() - 1);
                    adapter.notifyDataSetChanged();
                }

            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

}
