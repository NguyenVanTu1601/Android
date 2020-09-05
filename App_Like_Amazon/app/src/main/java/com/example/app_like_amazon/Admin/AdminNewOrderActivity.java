package com.example.app_like_amazon.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_like_amazon.Model.AdminOrders;
import com.example.app_like_amazon.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * + Hiển thị danh sách tất cả các đơn hàng đã đặt theo state của đơn hàng
 * + Click vào 1 itemView sẽ cho phép admin xóa đơn hàng hoặc không
 * + Long click vào 1 item sẽ cho phép admin xác nhận ship đơn hàng
 */
public class AdminNewOrderActivity extends AppCompatActivity {
    private RecyclerView ordersList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);

        initialization();
    }

    // Đọc danh sách các đơn hàng đã đặt theo tiêu chí các đơn có trạng thái state = not shipped
    // Nếu ko có điều kiện này sẽ thành đọc tất cả các đơn
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersRef.orderByChild("state").equalTo("not shipped"), AdminOrders.class)
                .build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {
                        holder.UserName.setText("Người nhận : " + model.getName());
                        holder.userDateTime.setText("Thời gian : " + model.getDate() + " " + model.getTime());
                        holder.userPhoneNumber.setText("Số điện thoại : " + model.getPhone());
                        holder.userTotalPrice.setText( "Total Amount = "+ model.getTotalAmount() + "$");
                        holder.userShippingAddress.setText(" Địa chỉ nhận hàng : " + model.getAddress() + " " + model.getCity());

                        holder.showOrdetBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uID = getRef(position).getKey(); // gửi key để xem chi tiết đơn hàng
                                Intent intent = new Intent(AdminNewOrderActivity.this, AdminUserProductsActivity.class);
                                //intent.putExtra("pid", model.getPhone());
                                intent.putExtra("pid",uID);
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrderActivity.this);
                                builder.setTitle("Xác nhận xóa đơn hàng ?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // which là vị trí trong mảng options
                                        if(which == 0){
                                            String uId = getRef(position).getKey();
                                            RemoveOrder(uId);
                                        }
                                        if(which == 1){
                                            finish();
                                        }
                                    }
                                });

                                builder.show();
                            }
                        });

                        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrderActivity.this);
                                builder.setTitle("Xác nhận gửi đơn hàng ?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // which là vị trí trong mảng options
                                        if(which == 0){
                                            String uId = getRef(position).getKey(); // lấy ra key
                                            // Xác nhận đơn hàng thành shipped

                                            Map<String,Object> Ormap = new HashMap<>();
                                            Ormap.put("state","shipped"); // đổi như này để được mua thêm hàng mới :)) chứ thật ra nó là shipped
                                            ordersRef.child(uId).updateChildren(Ormap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(AdminNewOrderActivity.this, "Đơn hàng đã được ship", Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                        }
                                        if(which == 1){
                                            finish();
                                        }
                                    }
                                });

                                builder.show();
                                return true;
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };

        ordersList.setAdapter(adapter);
        adapter.startListening();
    }

    private void RemoveOrder(String uId) {
        ordersRef.child(uId).removeValue();
    }

    // View holder để hiển thị
    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{
        private TextView UserName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
        private Button showOrdetBtn;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            UserName = itemView.findViewById(R.id.orders_user_name);
            userPhoneNumber = itemView.findViewById(R.id.orders_phone_number);
            userTotalPrice = itemView.findViewById(R.id.orders_total_price);
            userDateTime = itemView.findViewById(R.id.orders_date_time);
            userShippingAddress = itemView.findViewById(R.id.orders_address_city);
            showOrdetBtn = itemView.findViewById(R.id.show_orders_product_btn);



        }
    }

    // Khởi tạo
    private void initialization() {
        ordersList = findViewById(R.id.admin_list_new_orders);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
    }
}
