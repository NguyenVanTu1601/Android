package com.example.app_like_amazon.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_like_amazon.Buyers.HomeActivity;
import com.example.app_like_amazon.Model.Products;
import com.example.app_like_amazon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

/**
 * Cho phép admin sửa thông tin sản phẩm
 */
public class AdminMaintainProductActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText name, price, description;
    private Button applyChangeBtn, deleteBtn;
    private String productID = "";
    private DatabaseReference productRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_product);

        productID = getIntent().getStringExtra("pid");

        initialization();

        getProductDetails(productID);


        applyChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChange(productID);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });

    }

    private void deleteProduct() {
        productRef.child(productID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(AdminMaintainProductActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(AdminMaintainProductActivity.this, "Xóa sản phẩm thành công!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyChange(String productID) {
        String nameProduct = name.getText().toString();
        String priceProduct = price.getText().toString();

        String descriptionProduct = description.getText().toString();

        if(TextUtils.isEmpty(nameProduct)){
            Toast.makeText(this, "Thêm tên sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(priceProduct)){
            Toast.makeText(this, "Thêm giá sản phẩm ", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(descriptionProduct)){
            Toast.makeText(this, "Thêm mô tả sản phẩm", Toast.LENGTH_SHORT).show();
        }else{

            Map<String, Object> mapProduct = new HashMap<>();
            mapProduct.put("pname",nameProduct);
            mapProduct.put("pid",productID);
            mapProduct.put("price",priceProduct);
            mapProduct.put("description",descriptionProduct);

            productRef.child(productID).updateChildren(mapProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AdminMaintainProductActivity.this, "Change information product", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminMaintainProductActivity.this, AdminCategoryActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(AdminMaintainProductActivity.this, "Change error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void getProductDetails(String productID) {
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);

                    name.setText(products.getPname());
                    price.setText(products.getPrice());
                    description.setText(products.getDescription());

                    Picasso.with(AdminMaintainProductActivity.this)
                            .load(products.getImage())
                            .into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AdminMaintainProductActivity.this, AdminCategoryActivity.class);
        startActivity(intent);
        finish();
    }

    private void initialization() {
        imageView = findViewById(R.id.maintain_product_image);
        name = findViewById(R.id.maintain_product_name);
        price = findViewById(R.id.maintain_product_price);
        description = findViewById(R.id.maintain_product_description);
        applyChangeBtn = findViewById(R.id.maintain_product_change_btn);
        deleteBtn = findViewById(R.id.maintain_delete_btn);
    }
}
