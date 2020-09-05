package com.example.blogsapp.Home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.blogsapp.Comment.CommentActivity;
import com.example.blogsapp.PushNotification.Client;
import com.example.blogsapp.PushNotification.Data;
import com.example.blogsapp.PushNotification.Sender;
import com.example.blogsapp.PushNotification.Token;
import com.example.blogsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    private List<BlogPhoto> listBlogPhoto;
    private Context context;

    public BlogRecyclerAdapter(List<BlogPhoto> listBlogPhoto, Context context) {
        this.listBlogPhoto = listBlogPhoto;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.blog_list_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String desc_data = listBlogPhoto.get(position).getDescription();
        final String image = listBlogPhoto.get(position).getImage();
        final String date = listBlogPhoto.get(position).getDatetime();
        final String uid = listBlogPhoto.get(position).getUid(); // id người viết blog
        final String key = listBlogPhoto.get(position).blogId; // key của blog
        final String currentId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // id người dùng hiện tại

        holder.textDescription.setText(desc_data);
        Glide.with(context).load(image).into(holder.imageBlog);
        holder.textDate.setText(date);

        //------------------------------------- Xử lý like ----------------------------
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        holder.checkFavorite(currentId, key, rootRef);

        rootRef.child("Users").child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();
                            holder.textUserName.setText(retrieveUserName);

                            RequestOptions placeholder = new RequestOptions();
                            placeholder.placeholder(R.drawable.profile_image);
                            Glide.with(context).applyDefaultRequestOptions(placeholder)
                                    .load(retrieveProfileImage).into(holder.imageProfle);

                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        APIService apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        holder.imageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.favorite) {
                    // Xóa khỏi like
                    rootRef.child("Blog").child(key).child("Likes").child(currentId).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    rootRef.child("Blog").child(key).child("Likes").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            holder.textNumberFavorite.setText(dataSnapshot.getChildrenCount() + " likes");
                                            holder.imageFavorite.setImageResource(R.drawable.ic_favorite);
                                            holder.favorite = false;
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            });

                    // Xóa khỏi Notification
                    // Không cần do vd khi like nó sẽ thông báo đã like nhưng hủy like thì không thông báo gì

                } else {

                    //holder.textNumberFavorite.setText("1 likes");
                    Map<String, Object> mapLike = new HashMap<>();
                    final String dateTime = new SimpleDateFormat("EEEE, dd MMMM yyyy HH : mm a",
                            Locale.getDefault()).format(new Date());
                    mapLike.put(currentId, dateTime);

                    // Thêm vào key likes
                    rootRef.child("Blog").child(key).child("Likes").updateChildren(mapLike)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    rootRef.child("Blog").child(key).child("Likes").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            holder.textNumberFavorite.setText(dataSnapshot.getChildrenCount() + " likes");
                                            holder.favorite = true;
                                            holder.imageFavorite.setImageResource(R.drawable.ic_favorite_2);

                                            // Thêm vào notification
                                            Map<String, Object> mapNotification = new HashMap<>();
                                            mapNotification.put("time", dateTime); // id người like
                                            mapNotification.put("id",currentId);
                                            mapNotification.put("idBlog",key);
                                            if(!uid.equals(currentId)){
                                                rootRef.child("Notification").child(uid).child("Likes").child(currentId).updateChildren(mapNotification)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                            }
                                                        });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            });

                    // sendNotification(uid, currentId, "likes");

                }
            }
        });

        //------------------Xu ly comment----------------------
        holder.imageComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), CommentActivity.class);
                intent.putExtra("key",key);
                intent.putExtra("currentId", currentId);
                context.startActivity(intent);
            }
        });

        holder.textNumberComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), CommentActivity.class);
                intent.putExtra("key",key);
                intent.putExtra("currentId", currentId);
                intent.putExtra("uid", uid);
                context.startActivity(intent);
            }
        });

        rootRef.child("Blog").child(key).child("Comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.textNumberComment.setText(dataSnapshot.getChildrenCount() + " Comment");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    // Gửi nhận notification - chưa test
    private void sendNotification(final String uid, final String currentId, String likes) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);

                    Data data = new Data(currentId, "No image", "Tú đã like bài viết của bạn", "New Message", uid);
                    Sender sender = new Sender(data, token.getToken());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return listBlogPhoto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textUserName, textDate, textDescription, textNumberFavorite, textNumberComment;
        ImageView imageBlog;
        CircleImageView imageProfle;
        ImageView imageFavorite, imageComment;
        boolean favorite = false;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textDescription = itemView.findViewById(R.id.item_description);
            imageBlog = itemView.findViewById(R.id.item_image);
            imageProfle = itemView.findViewById(R.id.item_profile_image);
            textDate = itemView.findViewById(R.id.item_date_time);
            textUserName = itemView.findViewById(R.id.item_user_name);
            textNumberFavorite = itemView.findViewById(R.id.item_number_favorite);

            imageFavorite = itemView.findViewById(R.id.item_favorite);
            imageComment = itemView.findViewById(R.id.item_comment);
            textNumberComment = itemView.findViewById(R.id.item_comment_number);

        }

        private void checkFavorite(String currentId, String key, DatabaseReference rootRef) {
            rootRef.child("Blog").child(key).child("Likes").child(currentId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                favorite = true;
                                imageFavorite.setImageResource(R.drawable.ic_favorite_2);
                            } else {
                                favorite = false;
                                imageFavorite.setImageResource(R.drawable.ic_favorite);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

            rootRef.child("Blog").child(key).child("Likes")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                textNumberFavorite.setText(dataSnapshot.getChildrenCount() + " likes");
                            } else {
                                textNumberFavorite.setText("0 likes");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }

    }
}
