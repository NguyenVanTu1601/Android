package com.example.checkbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CheckBox cbJava;
    CheckBox cbC ;
    CheckBox cbPHP;
    Button btx ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cbJava = (CheckBox) findViewById(R.id.checkBox);
        cbC = (CheckBox) findViewById(R.id.checkBox2);
        cbPHP = (CheckBox) findViewById(R.id.checkBox3);
        btx = (Button) findViewById(R.id.button);
        cbJava.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(MainActivity.this, "Bạn đã chọn môn Java", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Bạn vừa hủy chọn môn Java", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btx.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String monhoc = "Danh sách môn học bạn đã chọn\n";
                if(cbJava.isChecked()){
                    monhoc += cbJava.getText() + "\n";
                }
                if(cbC.isChecked()) {
                    monhoc += cbC.getText() + "\n";
                }
                if(cbPHP.isChecked()){
                    monhoc += cbPHP.getText();
                }
                Toast.makeText(MainActivity.this, monhoc, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
