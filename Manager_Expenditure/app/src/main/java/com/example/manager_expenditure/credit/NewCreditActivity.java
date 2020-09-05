package com.example.manager_expenditure.credit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.manager_expenditure.R;

public class NewCreditActivity extends AppCompatActivity {

    private String creditType;
    private CardView cardViewThuongxuyen, cardViewNo, cardViewTietKiem;
    private ImageButton imgClose, imgAddNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_credit);

        creditType = getIntent().getStringExtra("credit");
        cardViewThuongxuyen = findViewById(R.id.cardviewThuongxuyen);
        cardViewNo = findViewById(R.id.cardviewNo);
        cardViewTietKiem = findViewById(R.id.cardviewTietkiem);
        imgClose = findViewById(R.id.close_new_credit);
        imgAddNew = findViewById(R.id.add_new_credit);

        if(creditType.equals("Thường xuyên")){
            cardViewThuongxuyen.setVisibility(View.VISIBLE);
            cardViewNo.setVisibility(View.GONE);
            cardViewTietKiem.setVisibility(View.GONE);
        }else if(creditType.equals("Nợ")){
            cardViewThuongxuyen.setVisibility(View.GONE);
            cardViewNo.setVisibility(View.VISIBLE);
            cardViewTietKiem.setVisibility(View.GONE);
        }else if(creditType.equals("Tiết kiệm")){
            cardViewThuongxuyen.setVisibility(View.GONE);
            cardViewNo.setVisibility(View.GONE);
            cardViewTietKiem.setVisibility(View.VISIBLE);
        }

        imgAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewCreditActivity.this, "Thêm tài khoản mới", Toast.LENGTH_SHORT).show();
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}