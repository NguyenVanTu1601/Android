package com.example.serviceorientedsoftware.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.serviceorientedsoftware.R;
import com.example.serviceorientedsoftware.constants.Constants;
import com.example.serviceorientedsoftware.databinding.ActivityProfileUserBinding;
import com.example.serviceorientedsoftware.model.User;
import com.example.serviceorientedsoftware.retrofit.APIUtils;
import com.example.serviceorientedsoftware.retrofit.DataClient;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUserActivity extends AppCompatActivity {

    private ActivityProfileUserBinding binding;
    private String currentUID;
    private Uri resultUri;
    private FirebaseAuth mAuth;
    private StorageReference myPostImageBlogUrl;
    private StorageTask uploadTask; // up image lên fireStorage
    private String myDownloadImage;
    private ProgressDialog dialog;
    private String imageAvata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        readInfoUserFromFirebase();

        // croper ảnh
        binding.imgAvataProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // để sử dụng thì thêm quyền đọc ghi camera, thêm activity của croper vào manifest
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(binding.imgAvataProfile.getWidth(),binding.imgAvataProfile.getHeight())
                        .setAspectRatio(1,1)
                        .start(ProfileUserActivity.this);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Đang cập nhật thông tin...");
                dialog.setMessage("Vui lòng đợi trong giây lát...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                uploadImage();


            }
        });

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileUserActivity.this, PetsActivity.class));
                finish();
            }
        });

        binding.layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyBoard();
            }
        });
    }

    private void readInfoUserFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        binding.emailProfile.setText(document.getString(Constants.KEY_EMAIL));
                        binding.nameProfile.setText(document.getString(Constants.KEY_NAME));
                        String address = document.getString(Constants.KEY_ADDESS);
                        if (!TextUtils.isEmpty(address)){
                            binding.addressProfile.setText(address);
                        }
                        imageAvata = document.getString(Constants.KEY_IMAGE);
                        if(!TextUtils.isEmpty(imageAvata)){

                            Glide.with(ProfileUserActivity.this)
                                    .load(imageAvata)
                                    .centerCrop()
                                    .placeholder(R.drawable.profile_image)
                                    .into(binding.imgAvataProfile);
                        }
                    } else {
                        Toast.makeText(ProfileUserActivity.this, "No profile", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileUserActivity.this, "No Profile ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        myPostImageBlogUrl = FirebaseStorage.getInstance().getReference();
        currentUID = mAuth.getCurrentUser().getUid();
        dialog = new ProgressDialog(this);
        myDownloadImage = "";

        // Hide Status Bar
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            View decorView = getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    // Nhận ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                binding.imgAvataProfile.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }
    }

    public void uploadImage(){
        Calendar calendar = Calendar.getInstance();
        long milis = calendar.getTimeInMillis();
        final StorageReference filepath = myPostImageBlogUrl.child(currentUID).child(milis + "." + "jpg");
        if (resultUri == null ){
            if (TextUtils.isEmpty(imageAvata)){
                addToDatabase("");
            }else{
                addToDatabase(imageAvata);
            }

        }else{
            uploadTask = filepath.putFile(resultUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()) {
                        Uri downloadUri = (Uri) task.getResult();
                        myDownloadImage = downloadUri.toString();
                        addToDatabase(myDownloadImage);
                        dialog.dismiss();
                        //Toast.makeText(ProfileUserActivity.this, myDownloadImage, Toast.LENGTH_SHORT).show();
                    }else{
                        dialog.dismiss();
                    }
                }
            });
        }

    }

    private void addToDatabase(String urlImage){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Hashtable<String, Object> user = new Hashtable<>();

        user.put(Constants.KEY_NAME, binding.nameProfile.getText().toString());
        user.put(Constants.KEY_EMAIL, binding.emailProfile.getText().toString());
        user.put(Constants.KEY_ADDESS, binding.addressProfile.getText().toString());
        user.put(Constants.KEY_IMAGE, urlImage);

        database.collection(Constants.KEY_COLLECTION_USER).document(mAuth.getCurrentUser().getUid())
                .set(user, SetOptions.merge())
                .addOnCompleteListener(task -> {
                    //Toast.makeText(this, "Update info user to firbase firestore", Toast.LENGTH_SHORT).show();
                    //DataClient dataClient = APIUtils.getData(Constants.base_url);
                    DataClient dataClient = APIUtils.getData(Constants.user_url);
                    User u = new User(mAuth.getCurrentUser().getUid(), binding.emailProfile.getText().toString(),
                            binding.addressProfile.getText().toString(), binding.nameProfile.getText().toString(),urlImage);
                    Call<User> createUser = dataClient.createUser(u);
                    createUser.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            //Toast.makeText(ProfileUserActivity.this, "add user to server" + response.body().getId() , Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            startActivity(new Intent(ProfileUserActivity.this, PetsActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });

                })
                .addOnFailureListener(exception -> {
                    dialog.dismiss();
                    Toast.makeText(this, "Fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)
                    view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            // Hide the soft keyboard
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}