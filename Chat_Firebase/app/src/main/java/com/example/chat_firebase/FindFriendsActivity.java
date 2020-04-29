package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Chứa danh sách người có thể liên hệ
 */
public class FindFriendsActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private RecyclerView FindFriendsRecycleList;
    private DatabaseReference UserRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        // Khởi tạo
        InitializeField();

    }

    // Khởi tạo ban đầu
    private void InitializeField() {
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        FindFriendsRecycleList = findViewById(R.id.find_friends_recycle_list);
        FindFriendsRecycleList.setLayoutManager(new LinearLayoutManager(this));

        mToolBar = findViewById(R.id.find_friends_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Friends");
    }

    // Hiển thị view và bắt sự kiện
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Contacts> options =
                new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(UserRef,Contacts.class)
                .build();
        FirebaseRecyclerAdapter<Contacts,FindFriendViewHolder> adapter =
               new FirebaseRecyclerAdapter<Contacts, FindFriendViewHolder>(options) {
                   @Override
                   protected void onBindViewHolder(@NonNull final FindFriendViewHolder holder, final int position, @NonNull Contacts model) {
                       // Dữ liệu lấy về để lần lượt trong model
                       holder.userName.setText(model.getName());
                       holder.userStatus.setText(model.getStatus());
                       Picasso.with(FindFriendsActivity.this).load(model.getImage())
                               .placeholder(R.drawable.profile_image)
                               .into(holder.profileImage);

                       // Bắt sự kiện click
                       holder.itemView.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               String visit_user_id = getRef(position).getKey(); // key này chính là tên nhánh trên cây database

                               // Chuyển key sang Activity profile để xem thông tin :))
                               ChangeActivity(holder,visit_user_id);

                           }
                       });
                   }

                   @NonNull
                   @Override
                   public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout,parent,false);
                       FindFriendViewHolder viewHolder = new FindFriendViewHolder(view);
                       return viewHolder;
                   }
               };
        adapter.notifyDataSetChanged();
        FindFriendsRecycleList.setAdapter(adapter);
        adapter.startListening();
    }

    // Tạo viewHolder
    public static class FindFriendViewHolder extends RecyclerView.ViewHolder{

        TextView userName, userStatus;
        CircleImageView profileImage;
        ImageView userOnline;
        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_profile_name);
            userStatus = itemView.findViewById(R.id.user_status);
            userOnline = itemView.findViewById(R.id.user_online_status);
            profileImage = itemView.findViewById(R.id.users_profile_image);
        }
    }

    // Chuyển đổi màn hình để hiển thị thông tin  user đang chọn và gửi yêu cầu kb/ xóa yêu cầu/ xóa liên hệ
    private void ChangeActivity(FindFriendViewHolder holder, String visit_id){
        Intent profileIntent = new Intent(FindFriendsActivity.this, ProfileActivity.class);
        profileIntent.putExtra("visit_user_id",visit_id);

        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View,String>(holder.profileImage,"imageTransition");
        pairs[1] = new Pair<View,String>(holder.userName,"nameTransition");
        pairs[2] = new Pair<View,String>(holder.userStatus,"descTransition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FindFriendsActivity.this,pairs);

        startActivity(profileIntent,options.toBundle());
    }

}
