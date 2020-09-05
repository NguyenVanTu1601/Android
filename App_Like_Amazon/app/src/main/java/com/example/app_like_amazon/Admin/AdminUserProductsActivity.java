package com.example.app_like_amazon.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_like_amazon.Model.Cart;
import com.example.app_like_amazon.R;
import com.example.app_like_amazon.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Hiển thị danh sách chi tiết các sản phẩm mà đơn hàng này đã đặt với key đơn hàng lấy từ Activity AdminNewOrdersActivity
 */
public class AdminUserProductsActivity extends AppCompatActivity {
    private RecyclerView productList;
    private DatabaseReference cartListRef;
    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        initialization();
    }


    // Đọc thông tin chi tiết của đơn hàng có id đã nhận về
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef, Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                        holder.txtProductQuanlity.setText("Quantity = " + model.getQuanlity());
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductPrice.setText( "Price : " + model.getPrice() + "$");
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };
        productList.setAdapter(adapter);
        adapter.startListening();
    }

    // Khởi tạo
    private void initialization() {
        productList = findViewById(R.id.products_list);
        productList.setLayoutManager(new LinearLayoutManager(this));

        userId = getIntent().getStringExtra("pid");

        cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Admin View").child(userId).child("Products");

    }
}
