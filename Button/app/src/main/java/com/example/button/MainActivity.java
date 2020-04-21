package com.example.button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Button btnClick;
    Button btnUnClick;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textViewNoidung);
        btnClick = findViewById(R.id.buttonClick);
        btnUnClick = findViewById(R.id.buttonUnClick);
        s = (String)tv.getText();
        // final String s = .....
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Hello Tú Nguyễn");
            }
        });
        btnUnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(s);
            }
        });

    }
}
