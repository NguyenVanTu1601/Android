package com.example.blogsapp.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blogsapp.MainActivity;
import com.example.blogsapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewPostActivity extends AppCompatActivity {

    private Toolbar postToolbar;
    private EditText textDescription;
    private ImageView imageNewPost;
    private Button btnPost;
    private ProgressDialog loadingBar;

    private Uri resultUri;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private String currentUID;
    private StorageReference myPostImageBlogUrl;
    private StorageReference allPostImageBlogUrl;
    private StorageTask uploadTask; // up image lên fireStorage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        initialization();

        setSupportActionBar(postToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("New Post");

        imageNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(imageNewPost.getWidth(),imageNewPost.getHeight())
                        .setAspectRatio(1,1)
                        .start(NewPostActivity.this);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadBlog();
            }
        });
    }

    private void upLoadBlog() {
        final String description = textDescription.getText().toString();
        /* Thêm EEEE để xác định thứ mấy */
        final String dateTime = new SimpleDateFormat("dd MMMM yyyy HH : mm a", Locale.getDefault()).format(new Date());
        if(TextUtils.isEmpty(description)){
            Toast.makeText(NewPostActivity.this, "Mô tả thêm về bức ảnh của " +
                    "bạn với mọi người nào =))", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingBar.setTitle("upload bloger...");
        loadingBar.setMessage("Vui lòng đợi trong giây lát...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // Tạo random key cho ảnh
        DatabaseReference userImageKeyRef = rootRef.child("Blog Images").push();
        final String imagePushID = userImageKeyRef.getKey();

        final StorageReference filepath = myPostImageBlogUrl.child("Post Images").child(currentUID).child(imagePushID + "." + "jpg");
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
                if(task.isSuccessful()){
                    Uri downloadUri = (Uri) task.getResult();
                    String myDownload = downloadUri.toString();

                    Map postImage = new HashMap<String, Object>();
                    postImage.put("uid",currentUID);
                    postImage.put("image", myDownload);
                    postImage.put("description",description);
                    postImage.put("datetime",dateTime);

                    rootRef.child("Blog").child(imagePushID).updateChildren(postImage)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    loadingBar.dismiss();
                                    Toast.makeText(NewPostActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(NewPostActivity.this, MainActivity.class));
                                    finish();
                                }
                            });
                    loadingBar.dismiss();
                }else{
                    loadingBar.dismiss();
                    Toast.makeText(NewPostActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialization() {

        postToolbar = findViewById(R.id.toolbar_new_post);
        btnPost = findViewById(R.id.button_new_post);
        imageNewPost = findViewById(R.id.new_post_image);
        textDescription = findViewById(R.id.text_description_new_post);
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        currentUID = mAuth.getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();
        myPostImageBlogUrl = FirebaseStorage.getInstance().getReference();
        allPostImageBlogUrl = FirebaseStorage.getInstance().getReference();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imageNewPost.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }
    }
}