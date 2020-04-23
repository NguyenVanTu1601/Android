package com.example.chat_firebase;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessagesViewHolder> {
    private List<Messages> userMessagesList;
    Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    public MessageAdapter(List<Messages> userMessagesList) {
        this.userMessagesList = userMessagesList;
    }

    public MessageAdapter(List<Messages> userMessagesList, Context context) {
        this.userMessagesList = userMessagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_messages_layout, parent, false);
        mAuth = FirebaseAuth.getInstance();
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessagesViewHolder holder, int position) {
        // Lấy id của ng sử dụng
        String messageSenderID = mAuth.getCurrentUser().getUid();

        // Lấy tin nhắn tại vị trí position
        Messages messages = userMessagesList.get(position);

        // Kiểm tra tin nhắn do ai gửi
        String fromUserID = messages.getFrom();
        String fromMessageType = messages.getType();


        if (fromMessageType.equals("text")){
            holder.receiverMessageText.setVisibility(View.INVISIBLE);
            holder.receiverProfileImage.setVisibility(View.INVISIBLE);
            holder.senderMessageText.setVisibility(View.INVISIBLE);

            // Nếu tin nhắn do mình gửi
            if(fromUserID.equals(messageSenderID)){
                holder.senderMessageText.setVisibility(View.VISIBLE);
                holder.senderMessageText.setBackgroundResource(R.drawable.sender_messages_layout);
                holder.senderMessageText.setTextColor(Color.BLACK);
                holder.senderMessageText.setText(messages.getMessage());
            }else{

                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.receiverMessageText.setVisibility(View.VISIBLE);

                holder.receiverMessageText.setBackgroundResource(R.drawable.receiver_messages_layout);
                holder.receiverMessageText.setTextColor(Color.BLACK);
                holder.receiverMessageText.setText(messages.getMessage());

                // Lấy thông tin người gửi tin nhắn

                usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);
                usersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("image")){
                            String receiveImage = dataSnapshot.child("image").getValue().toString();
                            Picasso .with(context)
                                    .load(receiveImage)
                                    .placeholder(R.drawable.profile_image)
                                    .into(holder.receiverProfileImage);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

    public class MessagesViewHolder extends RecyclerView.ViewHolder{

        public TextView senderMessageText, receiverMessageText;
        public CircleImageView receiverProfileImage;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessageText = itemView.findViewById(R.id.sender_messages_text);
            receiverMessageText = itemView.findViewById(R.id.receiver_messages_text);
            receiverProfileImage = itemView.findViewById(R.id.messages_profile_image);

        }
    }
}
