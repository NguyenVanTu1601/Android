package com.example.quytrinhthietkeungdung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtNoidung; // khai báo toàn cục
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Ánh xạ :
        txtNoidung = (TextView)findViewById(R.id.textview1);
        btn = (Button) findViewById(R.id.button);
        // Viết code :
        String s = "Hello Tu Nguyen";
        txtNoidung.setText(s);
        btn.setText("Cộng");
    }
}
