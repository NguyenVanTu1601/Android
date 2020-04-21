package com.example.bt_randomorg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    EditText editText3;
    Button button;
    int number3;
    int number1;
    int number2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Anh xa
        Anhxa();
        // Click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = editText1.getText().toString();
                String s2 = editText2.getText().toString();
                try{
                    number1 = Integer.parseInt(s1);
                }catch(NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Please import Number on Min", Toast.LENGTH_SHORT).show();
                    editText1.requestFocus();
                    return;
                }
                try{
                    number2 = Integer.parseInt(s2);
                }catch(NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Please import Number on Max", Toast.LENGTH_SHORT).show();
                    editText2.requestFocus();
                    return;
                }
                Random random = new Random();
                number3 = number1 + random.nextInt(number2 - number1 + 1);
                editText3.setText(Integer.toString(number3));
            }
        });
    }
    private void Anhxa(){
        editText1 = findViewById(R.id.editText2);
        editText2 = findViewById(R.id.editText3);
        editText3 = findViewById(R.id.editText5);
        button = findViewById(R.id.button2);
    }
}
