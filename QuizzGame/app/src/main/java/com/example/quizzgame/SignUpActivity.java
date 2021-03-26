package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.quizzgame.databinding.ActivitySignUpBinding;
import com.example.quizzgame.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore database;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Create new account...!");

        binding.createNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                String email = binding.emailSignup.getText().toString();
                String password = binding.passwordSignup.getText().toString();
                String name = binding.fullnameSignup.getText().toString();
                String referCode = binding.recodeSignup.getText().toString();

                User user = new User(name, email, password, referCode);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    // Lấy uid của ng dùng khi ng dùng đã đăng ký tài khoản
                                    String uid = task.getResult().getUser().getUid();
                                    database.collection("users")
                                            .document(uid)
                                            .set(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    dialog.dismiss();
                                                    if (task.isSuccessful()){
                                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                                    }else{
                                                        Toast.makeText(SignUpActivity.this, "Error : " + task.getException().getLocalizedMessage(),
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }else{

                                    Toast.makeText(SignUpActivity.this, "Error : " + task.getException().getLocalizedMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });

            }
        });

        binding.haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}