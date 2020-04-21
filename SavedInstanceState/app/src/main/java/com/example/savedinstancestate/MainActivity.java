package com.example.savedinstancestate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtClick;
    Button btnClick;
    int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClick = findViewById(R.id.buttonClick);
        txtClick = findViewById(R.id.textViewClick);
        /*
        if(savedInstanceState != null){
            txtClick.setText("Lần thứ " + savedInstanceState.getInt("Hello"));
        }
        */
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count == 5) {
                    startActivity(new Intent(MainActivity.this,Activity_2.class));
                }else{
                    txtClick.setText("Click lần thứ " + count);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("Hello", count);
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        txtClick.setText("Resrore : " + savedInstanceState.getInt("Hello"));
    }
}
