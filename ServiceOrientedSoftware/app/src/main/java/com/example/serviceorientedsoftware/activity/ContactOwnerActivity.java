package com.example.serviceorientedsoftware.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.serviceorientedsoftware.R;
import com.example.serviceorientedsoftware.databinding.ActivityContactOwnerBinding;
import com.example.serviceorientedsoftware.model.User;

public class ContactOwnerActivity extends AppCompatActivity {

    private ActivityContactOwnerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactOwnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User owner = (User) getIntent().getSerializableExtra("owner");

        Glide.with(ContactOwnerActivity.this)
                .load(owner.getImage())
                .centerCrop()
                .placeholder(R.drawable.profile_image)
                .into(binding.imageOwner);

        binding.nameOwner.setText(owner.getName());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }
}