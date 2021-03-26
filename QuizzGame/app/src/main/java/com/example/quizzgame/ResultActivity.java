package com.example.quizzgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizzgame.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;
    private int POINTS = 10;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correct = getIntent().getIntExtra("correct",0);
        int total = getIntent().getIntExtra("total",0);

        int points = correct * POINTS;
        binding.scores.setText(String.format("%d/%d", correct,total));
        binding.coins.setText(points + "");

        // update coins lên database
        database = FirebaseFirestore.getInstance();
        
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points)); // cộng thêm vào kq quả, tránh việc đọc lại kq r cộng vào xong lưu lên

        // quay lại
        binding.restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finish();
            }
        });

        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}