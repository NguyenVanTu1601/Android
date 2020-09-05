package com.example.blogsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Load ảnh sử dụng Glide
 */
public class AccountSettingActivity extends AppCompatActivity {

    private Toolbar accountSettingToolBar;

    private FirebaseAuth mAuth;
    private String currentUID;
    private DatabaseReference rootRef;
    private StorageReference UserProfileImageRef;
    private StorageReference ImageUrl;

    private EditText edtUserName, edtPhone, edtAddress;
    private Button btnUpdate;
    private CircleImageView imageUser;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        initialization();

        setSupportActionBar(accountSettingToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Setting Account");

        readInforUser();

        // Lấy ảnh từ thu viện làm avata
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nên check quyền Permission trước khi đọc ảnh
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(AccountSettingActivity.this);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSetting();
            }
        });

    }

    private void initialization() {
        mAuth = FirebaseAuth.getInstance();
        currentUID = mAuth.getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");


        accountSettingToolBar = findViewById(R.id.toolbar_account_setting);
        edtAddress = findViewById(R.id.setting_profile_address);
        edtUserName = findViewById(R.id.setting_profile_name);
        edtPhone = findViewById(R.id.setting_profile_phone);
        imageUser = findViewById(R.id.setting_profile_image);
        btnUpdate = findViewById(R.id.update_setting_button);
        loadingBar = new ProgressDialog(this);

    }

    private void readInforUser(){
        rootRef.child("Users").child(currentUID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if((dataSnapshot.exists())&&(dataSnapshot.hasChild("name"))&&(dataSnapshot.hasChild("image"))){

                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveUserPhone = dataSnapshot.child("phone").getValue().toString();
                            String retrieveUserAddress = dataSnapshot.child("address").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                            edtUserName.setText(retrieveUserName);
                            edtPhone.setText(retrieveUserPhone);
                            edtAddress.setText(retrieveUserAddress);
                            Glide.with(AccountSettingActivity.this)
                                    .load(retrieveProfileImage)
                                    .error(R.drawable.profile_image)
                                    .into(imageUser);

                        }else if((dataSnapshot.exists())&&(dataSnapshot.hasChild("name")) && !dataSnapshot.hasChild("image")){

                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveUserPhone = dataSnapshot.child("phone").getValue().toString();
                            String retrieveUserAddress = dataSnapshot.child("address").getValue().toString();

                            edtUserName.setText(retrieveUserName);
                            edtPhone.setText(retrieveUserPhone);
                            edtAddress.setText(retrieveUserAddress);
                            imageUser.setImageResource(R.drawable.profile_image);

                        }else{

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void updateSetting(){
        String userName = edtUserName.getText().toString();
        String phone = edtPhone.getText().toString();
        String address = edtAddress.getText().toString();

        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)){
            Toast.makeText(this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }else{
            HashMap<String, Object> profileMap = new HashMap<>();
            profileMap.put("uid",currentUID);
            profileMap.put("name", userName);
            profileMap.put("phone",phone);
            profileMap.put("address",address);
            rootRef.child("Users").child(currentUID).updateChildren(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(AccountSettingActivity.this, MainActivity.class));
                                Toast.makeText(AccountSettingActivity.this, "Update Profile thành công...", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Log.d("ERROR",task.getException().getMessage());
                                Toast.makeText(AccountSettingActivity.this, "Update lỗi...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void addUriImageToDatabase(){

        ImageUrl = FirebaseStorage.getInstance().getReference().child("Profile Images").child(currentUID + ".jpg");
        ImageUrl.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Thêm link ảnh vào database
                rootRef.child("Users").child(currentUID).child("image").setValue(uri + "")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    loadingBar.dismiss();
                                }else{
                                    loadingBar.dismiss();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                final Uri resultUri = result.getUri();

                loadingBar.setTitle("Set profile image...");
                loadingBar.setMessage("Vui lòng đợi trong giây lát...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                // Đưa ảnh lên firebase
                StorageReference filepath = UserProfileImageRef.child(currentUID + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            // Đưa ảnh ra máy
                            imageUser.setImageURI(resultUri);

                            addUriImageToDatabase();

                            loadingBar.dismiss();
                            Toast.makeText(AccountSettingActivity.this, "Upload profile Image Success...", Toast.LENGTH_SHORT).show();
                        }else{
                            loadingBar.dismiss();
                            Toast.makeText(AccountSettingActivity.this, "Upload profile error...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }
    }
}