package com.example.androidb05activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class De1_Cau1 extends AppCompatActivity {

    EditText edt1, edt2;
    Button btn;
    RadioButton radioButton1, radioButton2;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de1__cau1);

        edt1 = findViewById(R.id.edtBK);
        edt2 = findViewById(R.id.edtCC);
        radioButton1 = findViewById(R.id.radioDT);
        radioButton2 = findViewById(R.id.radioTT);
        txtResult = findViewById(R.id.result);

        findViewById(R.id.btnCal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ban_kinh = Integer.parseInt(edt1.getText().toString());
                int chieu_cao = Integer.parseInt(edt2.getText().toString());

                double result = 0;
                if (radioButton1.isChecked()){
                    result = 3.14 * ban_kinh * ban_kinh + 2 * 3.14 * ban_kinh * chieu_cao;
                }else{
                    result = 3.14 * ban_kinh* ban_kinh * chieu_cao;

                }

                txtResult.setText(result + "");
            }
        });
    }
}