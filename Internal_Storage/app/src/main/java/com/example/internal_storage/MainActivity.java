package com.example.internal_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * 1. Lưu trữ trong bộ nhớ trong là gì
 * Lưu dữ liệu vào bên trong ứng dụng trên thiết bị, các ứng dụng khác ko có quyền truy cập . Dữ liệu bị xóa khi ứng dụng bị xóa hoặc chọn xóa dữ liệu bằng cleanData
 * Bộ nhớ trong là của ứng dụng chứ ko phải là bộ nhớ của máy
 * 2. Tại sao sử dụng
 * Lưu trũ được object, lưu ở dạng các file
 * 3. Có lẽ sẽ ko dùng nên thôi bỏ qua
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
