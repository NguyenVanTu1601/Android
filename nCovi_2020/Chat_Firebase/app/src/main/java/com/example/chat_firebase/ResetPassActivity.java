package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {
    Button btnSent;
    EditText edtEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        btnSent = findViewById(R.id.sentEmail);
        edtEmail = findViewById(R.id.textMail);
        mAuth = FirebaseAuth.getInstance();
        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ResetPassActivity.this, "Please provide email...", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPassActivity.this, "Please check email..",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPassActivity.this,Login_Activity.class));
                            }else{
                                String messages = task.getException().getMessage();
                                Toast.makeText(ResetPassActivity.this, "Error!!!" + messages, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
