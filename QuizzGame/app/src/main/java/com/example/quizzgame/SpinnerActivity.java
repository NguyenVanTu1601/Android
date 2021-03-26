package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quizzgame.SpinWheel.LuckyWheelView;
import com.example.quizzgame.SpinWheel.model.LuckyItem;
import com.example.quizzgame.databinding.ActivitySpinnerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {

    ActivitySpinnerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<LuckyItem> data = new ArrayList<>();

        LuckyItem item1 = new LuckyItem();
        item1.topText = "5";
        item1.secondaryText = "Coins";
        item1.color = Color.parseColor("#000000");
        item1.textColor = Color.parseColor("#FFFFFF");
        data.add(item1);

        LuckyItem item2 = new LuckyItem();
        item2.topText = "10";
        item2.secondaryText = "Coins";
        item2.color = Color.parseColor("#000000");
        item2.textColor = Color.parseColor("#FFFFFF");
        data.add(item2);

        LuckyItem item3 = new LuckyItem();
        item3.topText = "15";
        item3.secondaryText = "Coins";
        item3.color = Color.parseColor("#212121");
        item3.textColor = Color.parseColor("#eceff1");
        data.add(item3);

        LuckyItem item4 = new LuckyItem();
        item4.topText = "20";
        item4.secondaryText = "Coins";
        item4.color = Color.parseColor("#7f00d9");
        item3.textColor = Color.parseColor("#ffffff");
        data.add(item4);

        LuckyItem item5 = new LuckyItem();
        item5.topText = "25";
        item5.secondaryText = "Coins";
        item5.color = Color.parseColor("#212121");
        item5.textColor = Color.parseColor("#eceff1");
        data.add(item2);

        LuckyItem item6 = new LuckyItem();
        item6.topText = "30";
        item6.secondaryText = "Coins";
        item6.color = Color.parseColor("#dc0000");
        item6.textColor = Color.parseColor("#ffffff");
        data.add(item6);

        LuckyItem item7 = new LuckyItem();
        item7.topText = "5";
        item7.secondaryText = "Coins";
        item7.color = Color.parseColor("#212121");
        item7.textColor = Color.parseColor("#eceff1");
        data.add(item7);

        LuckyItem item8 = new LuckyItem();
        item8.topText = "0";
        item8.secondaryText = "Coins";
        item8.color = Color.parseColor("#008bff");
        item8.textColor = Color.parseColor("#ffffff");
        data.add(item8);

        binding.wheelView.setData(data);
        binding.wheelView.setRound(5);

        binding.spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int ranNumber = r.nextInt(8);

                binding.wheelView.startLuckyWheelWithTargetIndex(ranNumber);
            }
        });

        binding.wheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                updateCash(index);
                startActivity(new Intent(SpinnerActivity.this, MainActivity.class));
            }
        });
    }

    // cộng coins từ vòng quay vào coins cá nhân
    void  updateCash(int index){
        long cash = 0;
        switch (index) {
            case 0:
                cash = 5;
                break;
            case 1:
                cash = 10;
                break;
            case 2:
                cash = 15;
                break;
            case 3:
                cash = 20;
                break;
            case 4:
                cash = 25;
                break;

            case 5:
                cash = 30;
                break;
            case 6:
                cash = 35;
                break;
            case 7:
                cash = 0;
                break;
        }

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(cash))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SpinnerActivity.this, "Add coins to account!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}