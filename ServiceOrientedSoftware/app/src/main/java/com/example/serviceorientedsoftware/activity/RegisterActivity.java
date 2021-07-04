package com.example.serviceorientedsoftware.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.serviceorientedsoftware.constants.Constants;
import com.example.serviceorientedsoftware.databinding.ActivityRegisterBinding;
import com.example.serviceorientedsoftware.model.User;
import com.example.serviceorientedsoftware.retrofit.APIUtils;
import com.example.serviceorientedsoftware.retrofit.DataClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.iconBack.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });




        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setTitle("Đang tiến hành đăng ký...");
                dialog.setMessage("Vui lòng đợi trong giây lát...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                if (!TextUtils.isEmpty(binding.emailRegister.getText().toString())
                        && !TextUtils.isEmpty(binding.passwordRegister.getText().toString())){
                    registerAccount(binding.emailRegister.getText().toString(),
                            binding.passwordRegister.getText().toString());
                }else{
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
    }


    public void registerAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                            addToDatabase();

                        }else{
                            dialog.dismiss();
                            Toast.makeText(RegisterActivity.this,"Tạo tài khoản không thành công!" + task.getResult(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addToDatabase(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Hashtable<String, Object> user = new Hashtable<>();

        user.put(Constants.KEY_NAME, binding.nameRegister.getText().toString());
        user.put(Constants.KEY_EMAIL, binding.emailRegister.getText().toString());
        user.put(Constants.KEY_PASSWORD, binding.passwordRegister.getText().toString());

        try {
            database.collection(Constants.KEY_COLLECTION_USER).document(mAuth.getCurrentUser().getUid())
                    .set(user)
                    .addOnCompleteListener(task -> {
                        //Toast.makeText(this, "Add info user to firbase", Toast.LENGTH_SHORT).show();

                        //DataClient dataClient = APIUtils.getData(Constants.base_url); - cô v.a
                        DataClient dataClient = APIUtils.getData(Constants.user_url);
                        User u = new User(mAuth.getCurrentUser().getUid(), binding.emailRegister.getText().toString(),
                                "", binding.nameRegister.getText().toString(),"");
                        Call<User> createUser = dataClient.createUser(u);
                        createUser.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                //Toast.makeText(RegisterActivity.this, "add user to server" + response.body().getId() , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });

                        this.startActivity(new Intent(RegisterActivity.this, ProfileUserActivity.class));
                        dialog.dismiss();
                    })
                    .addOnFailureListener(exception -> {

                        Toast.makeText(this, "Failt : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    });
        }
        catch (Exception e){
            Toast.makeText(this, "regis fail", Toast.LENGTH_SHORT).show();
        }
    }

}