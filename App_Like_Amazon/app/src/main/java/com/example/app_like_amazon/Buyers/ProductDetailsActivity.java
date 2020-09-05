package com.example.app_like_amazon.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.app_like_amazon.Model.Products;
import com.example.app_like_amazon.Prevalent.Prevalent;
import com.example.app_like_amazon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {
    private FloatingActionButton addToCartBtn;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productName, productPrice, productDescription;
    private String productID = "" , state = "Normal";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        initialization();

        getProductDetails(productID);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state.equals("Order Placed") || state.equals("Order Shipped")){
                    Toast.makeText(ProductDetailsActivity.this, "Bạn không thể mua thêm sản phẩm khi đơn hang cũ chưa giao", Toast.LENGTH_SHORT).show();
                }else{
                    addingToCartList();
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        CheckOrdersState();


    }

    // Thêm sp vào giỏ hàng
    private void addingToCartList() {
        String saveCurrentTime = "";
        String saveCurrentDate = "";

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currenDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currenDate.format(calForDate.getTime());

        SimpleDateFormat currenTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currenTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final Map<String, Object> carMap = new HashMap<>();

        carMap.put("pid",productID);
        carMap.put("pname",productName.getText().toString());

        String pr = productPrice.getText().toString();
        pr = pr.substring(0,pr.length()-1);

        carMap.put("price",pr);
        carMap.put("date",saveCurrentDate);
        carMap.put("time",saveCurrentTime);
        carMap.put("quanlity",numberButton.getNumber());
        carMap.put("discount","");

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
        .child("Products").child(productID)
        .updateChildren(carMap)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    // Chưa hiểu lắm admin trong đây làm gì
                    cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                            .child("Products").child(productID)
                            .updateChildren(carMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ProductDetailsActivity.this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }


    // Đọc thông tin sản phẩm
    private void getProductDetails(final String productID) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice() + "$");
                    productDescription.setText(products.getDescription());

                    Picasso.with(ProductDetailsActivity.this)
                            .load(products.getImage())
                            .into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    // Đọc trạng thái đơn hàng
    private void CheckOrdersState(){
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("state").exists()){
                    String shippngState = dataSnapshot.child("state").getValue().toString();
                    if(shippngState.equals("shipped")){

                        state = "Order Shipped";

                    }else if(shippngState.equals("not shipped")){

                        state = "Order Placed";

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void initialization() {
        addToCartBtn = findViewById(R.id.add_product_to_cart_btn);
        productImage = findViewById(R.id.product_image_details);
        numberButton = findViewById(R.id.number_btn);
        productName  = findViewById(R.id.product_name_details);
        productDescription = findViewById(R.id.product_description_details);
        productPrice = findViewById(R.id.product_price_details);

    }
}
