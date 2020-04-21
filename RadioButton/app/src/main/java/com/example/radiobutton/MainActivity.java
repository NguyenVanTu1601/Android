package com.example.radiobutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    Button button;
    RadioButton radioButtonsang;
    RadioButton radioButtontrua;
    RadioButton radioButtontoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        button = (Button) findViewById(R.id.button);
        radioButtonsang = (RadioButton) findViewById(R.id.sang);
        radioButtontrua = (RadioButton) findViewById(R.id.trua);
        radioButtontoi = (RadioButton) findViewById(R.id.toi);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.sang:
                        Toast.makeText(MainActivity.this, "Bạn đã chọn buổi sáng", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.trua:
                        Toast.makeText(MainActivity.this, "Bạn đã chọn buổi trưa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.toi :
                        Toast.makeText(MainActivity.this, "Bạn đã chọn buổi tối", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioButtonsang.isChecked()){
                    Toast.makeText(MainActivity.this, "Bạn chọn sáng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
