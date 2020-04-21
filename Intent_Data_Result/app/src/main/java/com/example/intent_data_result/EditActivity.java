package com.example.intent_data_result;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    Button btnXacnhan;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        btnXacnhan = (Button) findViewById(R.id.buttonXacnhan);
        editText = (EditText) findViewById(R.id.editTextName);
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("tenmoi", s);
                setResult(RESULT_OK, intent); // mặc định
                finish(); // tắt màn hình hiện tại và trờ về màn hình trước đó
            }
        });
    }
}
