package com.example.app_like_amazon.Admin;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_like_amazon.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Cho phép admin chọn ảnh sản phầm từ máy và đưa lên firebase
 */
public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName, Description, price, Pname;
    private String saveCurrentDate, saveCurrentTime;
    private EditText ProductName, ProductDescription, ProductPrice;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getStringExtra("category");

        initialization();

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }

    private void ValidateProductData() {
        Description = ProductDescription.getText().toString();
        price = ProductPrice.getText().toString();
        Pname = ProductName.getText().toString();

        if(imageUri == null){
            Toast.makeText(this, "Vui lòng chọn ảnh cho sản phẩm", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Viết mô tả cho sản phẩm", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(price)){
            Toast.makeText(this, "Thêm giá cho sản phẩm", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Pname)){
            Toast.makeText(this, "Nhập tên cho sản phẩm", Toast.LENGTH_SHORT).show();
        }else{
            StoreProductInfomation();
        }
    }

    private void StoreProductInfomation() {

        loadingBar.setTitle("Add new product..");
        loadingBar.setMessage("Please wait ...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + "_" + saveCurrentTime;

        final StorageReference fileath = ProductImageRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = fileath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                String message = e.getMessage();
                Toast.makeText(AdminAddNewProductActivity.this,"Error... " +  message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this, "Image upload success", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw  task.getException();

                        }
                        downloadImageUrl = fileath.getDownloadUrl().toString();
                        return fileath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        downloadImageUrl = task.getResult().toString();
                        Toast.makeText(AdminAddNewProductActivity.this, "Product image save to database success", Toast.LENGTH_SHORT).show();
                        SaveProductInfoToDatabase();
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pId", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", categoryName);
        productMap.put("price", price);
        productMap.put("pname", Pname);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            loadingBar.dismiss();
                            Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);
                            Toast.makeText(AdminAddNewProductActivity.this, "Add product success!!", Toast.LENGTH_SHORT).show();
                        }else{
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Error : " +message , Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryPick && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            InputProductImage.setImageURI(imageUri);
        }
    }

    private void initialization() {
        AddNewProductButton     = findViewById(R.id.add_new_product);
        ProductName             = findViewById(R.id.product_name);
        ProductDescription      = findViewById(R.id.product_description);
        ProductPrice            = findViewById(R.id.product_price);
        InputProductImage       = findViewById(R.id.select_product_image);
        ProductImageRef         = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef             = FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar              = new ProgressDialog(this);
    }


}
