package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Thêm thư viện Circle
 * Name và status lưu vào database User
 * Ảnh lưu vào
 */
public class SettingsActivity extends AppCompatActivity {


    private Button updateAccountSetting;
    private EditText userName, userStatus;
    private CircleImageView userProfileImage;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private StorageReference UserProfileImageRef;
    private StorageReference ImageUrl;
    private ProgressDialog loadingBar;
    private Toolbar SettingsToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        InitializeFiled();

        userName.setVisibility(View.INVISIBLE); // nếu có thông tin thì ẩn cái text nhập tên đi

        RetrieveUserInfo();

        updateAccountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSettings();
            }
        });

        // Lấy ảnh từ thu viện làm avata
        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);
            }
        });

    }

    // Đọc thông tin ng dùng về từ database
    private void RetrieveUserInfo(){
        String currenUserID = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(currenUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if((dataSnapshot.exists())&&(dataSnapshot.hasChild("name"))&&(dataSnapshot.hasChild("image"))){

                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveUserStatus = dataSnapshot.child("status").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                            userName.setText(retrieveUserName);
                            userStatus.setText(retrieveUserStatus);
                            Picasso.with(SettingsActivity.this).load(retrieveProfileImage)
                                    .into(userProfileImage);

                        }else if((dataSnapshot.exists())&&(dataSnapshot.hasChild("name"))){

                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveUserStatus = dataSnapshot.child("status").getValue().toString();

                            userName.setText(retrieveUserName);
                            userStatus.setText(retrieveUserStatus);

                        }else{
                            userName.setVisibility(View.VISIBLE); // hiện text nếu chưa từng có thông tin
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    // Cập nhật thông tin, ko cập nhật ảnh
    private void UpdateSettings() {
        String setUserName = userName.getText().toString();
        String setStatus = userStatus.getText().toString();

        if(TextUtils.isEmpty(setUserName)){
            Toast.makeText(this, "Vui lòng nhập tên bạn...", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(setStatus)){
            Toast.makeText(this, "Thêm status của bạn", Toast.LENGTH_SHORT).show();
        }
        HashMap<String, Object> profileMap = new HashMap<>();
            profileMap.put("uid",currentUserID);
            profileMap.put("name", setUserName);
            profileMap.put("status",setStatus);
         RootRef.child("Users").child(currentUserID).updateChildren(profileMap)
         .addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful()){
                     SendUserToMainActivity();
                     Toast.makeText(SettingsActivity.this, "Update Profile thành công...", Toast.LENGTH_SHORT).show();
                 }
                 else{
                     Toast.makeText(SettingsActivity.this, "Update lỗi...", Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }

    // Khởi tạo
    private void InitializeFiled() {
        updateAccountSetting = findViewById(R.id.update_setting_button);
        userName = findViewById(R.id.set_user_name);
        userStatus = findViewById(R.id.set_profile_status);
        userProfileImage = findViewById(R.id.set_profile_image);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
        loadingBar = new ProgressDialog(this);

        SettingsToolBar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(SettingsToolBar);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    // Quay về Main
    private void SendUserToMainActivity(){
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Nhận kq lấy ảnh
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
                StorageReference filepath = UserProfileImageRef.child(currentUserID + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            // Đưa ảnh ra máy
                            //userProfileImage.setImageURI(resultUri);

                            AddUriImageToDatabase(RootRef,currentUserID);

                            loadingBar.dismiss();
                            Toast.makeText(SettingsActivity.this, "Upload profile Image Success...", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SettingsActivity.this, "Upload profile error...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }
    }

    private void AddUriImageToDatabase(final DatabaseReference Root, final String currentID){
        ImageUrl = FirebaseStorage.getInstance().getReference().child("Profile Images").child(currentUserID + ".jpg");
        ImageUrl.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Thêm link ảnh vào database
                Root.child("Users").child(currentID).child("image").setValue(uri + "")
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
}
