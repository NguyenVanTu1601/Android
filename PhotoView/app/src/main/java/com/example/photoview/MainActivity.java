package com.example.photoview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Mục tiêu dùng phóng to, thu nhỏ hình ảnh
 * Thêm thư viên
 * + Vào build.gradle của project thêm maven { url "https://jitpack.io" }
 * + Vào build.gradle của Module thêm implementation 'com.github.chrisbanes:PhotoView:2.3.0'
 */
public class MainActivity extends AppCompatActivity {
    PhotoView photoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoView = findViewById(R.id.photoView);

        photoView.setMaximumScale(5); // số lần room tối đa

        //photoView.setScale(). Room ảnh, thường dùng theo seekbar, kéo seekbar tới đâu ảnh tăng them tỉ lệ đó
        // Bắt vị trí chuột trên ảnh theo tọa độ x, y
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {

            }
        });
    }

}
