package com.example.videomeetingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.videomeetingapp.R;
import com.example.videomeetingapp.utilities.Constants;
import com.example.videomeetingapp.utilities.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Hashtable;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName, edtEmail, edtPassword, edtConfirmPassword;
    private MaterialButton btnSignUp;
    private String firstName, lastName, email, password, confirmPassword;
    private ProgressBar signUpProgessBar;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.imageBack).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.textSignIn).setOnClickListener(v -> onBackPressed());

        // khởi tạo
        init();

        btnSignUp.setOnClickListener(v -> {
            if (checkInput()){
                signUp();
            }
        });

    }

    // khởi tạo
    private void init() {
        edtFirstName        = findViewById(R.id.inputFirstName);
        edtLastName         = findViewById(R.id.inputLastName);
        edtEmail            = findViewById(R.id.inputEmail);
        edtPassword         = findViewById(R.id.inputPassword);
        edtConfirmPassword  = findViewById(R.id.inputConfirmPassword);
        btnSignUp           = findViewById(R.id.buttonSignUp);
        signUpProgessBar    = findViewById(R.id.progessSignUp);
        preferenceManager   = new PreferenceManager(getApplicationContext());
    }

    // kiểm tra các ô nhập liệu
    public boolean checkInput(){
        firstName       = edtFirstName.getText().toString();
        lastName        = edtLastName.getText().toString();
        email           = edtEmail.getText().toString();
        password        = edtPassword.getText().toString();
        confirmPassword = edtConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(firstName)){
            Toast.makeText(this, "First Name is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (lastName.isEmpty()){
            Toast.makeText(this, "Last Name is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(this, "ConfirmPassword is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!confirmPassword.equals(password)){
            Toast.makeText(this, "ConfirmPassword not correct!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // đăng kí thông tin
    private void signUp() {
        btnSignUp.setVisibility(View.INVISIBLE);
        signUpProgessBar.setVisibility(View.VISIBLE);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Hashtable<String, Object> user = new Hashtable<>();

        user.put(Constants.KEY_FIRST_NAME, firstName);
        user.put(Constants.KEY_LAST_NAME, lastName);
        user.put(Constants.KEY_EMAIL, email);
        user.put(Constants.KEY_PASSWORD, password);


        database.collection(Constants.KEY_COLLECTION_USER)
                .add(user)
                .addOnCompleteListener(task -> {

                    signUpProgessBar.setVisibility(View.INVISIBLE);

                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                    preferenceManager.putString(Constants.KEY_FIRST_NAME, firstName);
                    preferenceManager.putString(Constants.KEY_LAST_NAME, lastName);
                    preferenceManager.putString(Constants.KEY_EMAIL, email);
                    preferenceManager.putString(Constants.KEY_USER_ID, task.getResult().getId());
                    //Toast.makeText(this, "Id User : " + task.getResult().getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                })
                .addOnFailureListener(exception -> {
                    signUpProgessBar.setVisibility(View.INVISIBLE);
                    btnSignUp.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Failt : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}