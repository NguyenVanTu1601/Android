package com.example.android_bai3_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText edtNum1, edtNum2;
    private Button btnCal;
    private TextView txtRes;
    String[] st = {"+","-","*","/"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_01_bai_04);

        init();

        int x = spinner.getSelectedItemPosition();
        int num1 = Integer.parseInt(edtNum1.getText().toString());
        int num2 = Integer.parseInt(edtNum2.getText().toString());

        btnCal.setOnClickListener(v -> {
            switch (x){
                case 0:
                    btnCal.setText("ADD");
                   txtRes.setText("Result = " + (num1 + num2));
                   break;
                case 1:
                    btnCal.setText("SUB");
                    txtRes.setText("Result = " + (num1 - num2));
                    break;
                case 2:
                    btnCal.setText("MUL");
                    txtRes.setText("Result = " + (num1 * num2));
                    break;
                case 3:
                    btnCal.setText("DIV");
                    txtRes.setText("Result = " + (float)(num1*1.0 / num2));
                    break;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtRes.setText("Result : " + cal(st[position], num1, num2 ));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {
        spinner = findViewById(R.id.spinner);
        edtNum1 = findViewById(R.id.editText);
        edtNum2 = findViewById(R.id.editText2);
        btnCal  = findViewById(R.id.btnCal);
        txtRes  = findViewById(R.id.result);

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,st);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(arrayAdapter);
    }

    private int cal(String option, int n1, int n2){
        switch (option){
            case "+" :
                return n1 + n2;
            case "-" :
                return n1 - n2;
            case "*" :
                return n1 * n2;
            case "/" :
                return n1 / n2;
        }
        return 0;
    }
}