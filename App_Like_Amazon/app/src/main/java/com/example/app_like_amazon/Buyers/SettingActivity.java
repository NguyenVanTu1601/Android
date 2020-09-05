package com.example.app_like_amazon.Buyers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_like_amazon.Prevalent.Prevalent;
import com.example.app_like_amazon.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private CircleImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditext, addressEditText;
    private TextView profileChangeTextBtn, closeTextBtn,saveTextButton;
    private Button securityQuestionBtn;


    private Uri imageUri;
    private String myUri = "";
    private StorageReference storageProfilePictureRef;
    private String checker = "";
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initialiazation();

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked")){
                    userSaveInfo(); // lưu ảnh và thông tin nếu đã click chọn ảnh
                }else{
                    updateOnlyUserInfo(); //lưu thông tin
                }
            }
        });

        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(SettingActivity.this);
            }
        });

        securityQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ResetPasswordActivity.class);

                intent.putExtra("check","settings");
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profileImageView.setImageURI(imageUri);

        }else{
            Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingActivity.this, SettingActivity.class));
            finish();
        }
    }

    private void updateOnlyUserInfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        Map<String, Object> userMap = new HashMap<>();

        userMap.put("name", fullNameEditText.getText().toString());
        userMap.put("phoneOrder", userPhoneEditext.getText().toString());
        userMap.put("address", addressEditText.getText().toString());

        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);


        startActivity(new Intent(SettingActivity.this, HomeActivity.class));
        Toast.makeText(SettingActivity.this, "Update profile thành công", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void userSaveInfo() {
        String name = fullNameEditText.getText().toString();
        String phone = userPhoneEditext.getText().toString();
        String address = addressEditText.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
        }else if(checker.equals("clicked")){
            upLoadImage();
        }

    }

    private void upLoadImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update profile");
        progressDialog.setMessage("Vui lòng đợi giây lát...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if(imageUri != null){

            // Lưu ảnh vào fireStore
            final StorageReference fileRef = storageProfilePictureRef
                    .child(Prevalent.currentOnlineUser.getPhone() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw  task.getException();
                    }

                    // Lấy đường dẫn ảnh trong fileStore
                    return fileRef.getDownloadUrl();
                }
            })
            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){

                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();

                        // Lưu lại đường dẫn từ FireStore sang database và thông tin người dùng
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                        Map<String, Object> userMap = new HashMap<>();

                        userMap.put("name", fullNameEditText.getText().toString());
                        userMap.put("phoneOrder", userPhoneEditext.getText().toString());
                        userMap.put("address", addressEditText.getText().toString());
                        userMap.put("image", myUri);

                        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(SettingActivity.this, HomeActivity.class));
                        Toast.makeText(SettingActivity.this, "Update profile thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(SettingActivity.this, "Update error!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(this, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialiazation() {
        profileImageView        = findViewById(R.id.setting_profile_image);
        fullNameEditText        = findViewById(R.id.settings_full_nsme);
        userPhoneEditext        = findViewById(R.id.settings_phone_number);
        profileChangeTextBtn    = findViewById(R.id.profile_image_change_btn);
        closeTextBtn            = findViewById(R.id.close_settings_btn);
        saveTextButton          = findViewById(R.id.update_account_setting);
        addressEditText         = findViewById(R.id.setting_address);
        securityQuestionBtn     = findViewById(R.id.security_question_option);
        storageProfilePictureRef= FirebaseStorage.getInstance().getReference().child("Profile pictures");
        userInfoDisplay(profileImageView,fullNameEditText,userPhoneEditext,addressEditText);
    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameEditText, final EditText userPhoneEditext, final EditText addressEditText) {
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(Prevalent.currentOnlineUser.getPhone());

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    // Nếu đã cập nhật thông tin (có image) thì đọc thông tin về
                    if(dataSnapshot.child("image").exists()){
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phoneOrder").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        Picasso.with(SettingActivity.this).load(image).into(profileImageView);
                        fullNameEditText.setText(name);
                        userPhoneEditext.setText(phone);
                        addressEditText.setText(address);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
