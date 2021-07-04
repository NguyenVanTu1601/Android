package com.example.serviceorientedsoftware.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.serviceorientedsoftware.MainActivity;
import com.example.serviceorientedsoftware.R;
import com.example.serviceorientedsoftware.constants.Constants;
import com.example.serviceorientedsoftware.databinding.ActivityLoginBinding;
import com.example.serviceorientedsoftware.model.User;
import com.example.serviceorientedsoftware.retrofit.APIUtils;
import com.example.serviceorientedsoftware.retrofit.DataClient;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private ActivityLoginBinding binding;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        callbackManager = CallbackManager.Factory.create();

        mAuth = FirebaseAuth.getInstance();

        binding.loginFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email","public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, "Login fb thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailLogin.getText().toString();
                String password = binding.passwordLogin.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Email không được để trống!", Toast.LENGTH_SHORT).show();
                    binding.emailLogin.setFocusable(true);
                    return;
                }
                if (!binding.emailLogin.getText().toString().trim().matches(emailPattern)){
                    Toast.makeText(LoginActivity.this, "Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
                    binding.emailLogin.setFocusable(true);
                    return;

                }

                if (TextUtils.isEmpty(password) || password.length() < 6){
                    Toast.makeText(LoginActivity.this, "Password không nhỏ hơn 6 ký tự!", Toast.LENGTH_SHORT).show();
                    binding.passwordLogin.setFocusable(true);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, PetsActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        binding.textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!= null){
            updateUI(currentUser);
        }

    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Intent intent = new Intent(LoginActivity.this, PetsActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            addUserToFirebase(user);
                        }
                    }else{
                        addUserToFirebase(user);
                    }
                }
            });

            Intent intent = new Intent(LoginActivity.this, PetsActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Can not login with facebook!!!",Toast.LENGTH_SHORT).show();
        }

    }

    public void addUserToFirebase(FirebaseUser FU){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Hashtable<String, Object> user = new Hashtable<>();

        user.put(Constants.KEY_NAME, FU.getDisplayName());
        user.put(Constants.KEY_EMAIL, FU.getEmail());
        if (FU.getPhotoUrl() != null){
            user.put(Constants.KEY_IMAGE, FU.getPhotoUrl().toString());
        }else{
            user.put(Constants.KEY_IMAGE, "");
        }


        try {
            database.collection(Constants.KEY_COLLECTION_USER).document(mAuth.getCurrentUser().getUid())
                    .set(user)
                    .addOnCompleteListener(task -> {

                        //DataClient dataClient = APIUtils.getData(Constants.base_url);
                        DataClient dataClient = APIUtils.getData(Constants.user_url);
                        User u = new User(mAuth.getCurrentUser().getUid(), user.get(Constants.KEY_EMAIL).toString(),
                                "", user.get(Constants.KEY_NAME).toString(),user.get(Constants.KEY_IMAGE).toString());
                        Call<User> createUser = dataClient.createUser(u);
                        createUser.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {

                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });

                    })
                    .addOnFailureListener(exception -> {

                        Toast.makeText(this, "Failt : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}