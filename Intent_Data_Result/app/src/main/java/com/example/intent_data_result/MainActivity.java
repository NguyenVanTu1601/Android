package com.example.intent_data_result;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView txt;
    int REQUEST_CODE_EDIT = 13; //  tự đặt để kiểm tra dữ liệu trả về đúng của EditActivity ko
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Kich vao bunton se chuyen qua EditActivity
        // Khi kich vao button ben Edut thì nội dung sẽ chuyển lại về Main
        btn = (Button) findViewById(R.id.buttonEdit);
        txt = (TextView) findViewById(R.id.textViewName);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null){ // kiểm tra xem có phải dữ liệu từ activity kia chuyển về ko
            String ten = data.getStringExtra("tenmoi"); // Lấy data do data chính là intent
            txt.setText(ten);
        }
    }
}
