package com.example.chat_firebase;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    private View RequestsFragmentView;
    private RecyclerView myRequestList;

    private String currentUserID;
    private DatabaseReference ChatRequestRef;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private DatabaseReference ContactsRef;
    FirebaseRecyclerAdapter<Contacts,RequestsViewHolder> adapter;
    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RequestsFragmentView =  inflater.inflate(R.layout.fragment_requests, container, false);

        myRequestList = RequestsFragmentView.findViewById(R.id.chat_request_list);
        myRequestList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        ChatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        return RequestsFragmentView;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Contacts>  options =
                new FirebaseRecyclerOptions.Builder<Contacts>()
                        .setQuery(ChatRequestRef.child(currentUserID), Contacts.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Contacts, RequestsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RequestsViewHolder holder, int position, @NonNull Contacts model) {
                final String list_user_id = getRef(position).getKey(); // lấy từ key trong danh sách những ng đã từng liên hệ có thể là sent/ received
                final DatabaseReference getTypeRef = getRef(position).child("request_type");
                getTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String type = dataSnapshot.getValue().toString();

                            // Nếu người đó đã gửi tin yêu cầu cho mình
                            if(type.equals("received")){
                                UsersRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild("image")){
                                            String  requestUserName = dataSnapshot.child("name").getValue().toString();
                                            String  requestUserStatus = dataSnapshot.child("status").getValue().toString();
                                            String  requestProfileImage = dataSnapshot.child("image").getValue().toString();
                                            holder.userName.setText(requestUserName);
                                            holder.userStatus.setText(requestUserStatus);
                                            Picasso.with(getContext()).load(requestProfileImage)
                                                    .placeholder(R.drawable.profile_image)
                                                    .into(holder.profileImage);
                                        }else{
                                            String  requestUserName = dataSnapshot.child("name").getValue().toString();
                                            String  requestUserStatus = dataSnapshot.child("status").getValue().toString();
                                            holder.userName.setText(requestUserName);
                                            holder.userStatus.setText(requestUserStatus);
                                            holder.profileImage.setImageResource(R.drawable.profile_image);
                                        }

                                        // Hiển thị nút và bắt sự kiện
                                        holder.itemView.findViewById(R.id.request_accept_btn).setVisibility(View.VISIBLE);
                                        holder.itemView.findViewById(R.id.request_cancel_btn).setVisibility(View.VISIBLE);
                                        holder.AcceptButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ContactsRef.child(currentUserID).child(list_user_id).child("Contacts")
                                                        .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            ContactsRef.child(list_user_id).child(currentUserID).child("Contacts")
                                                                    .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        ChatRequestRef.child(currentUserID).child(list_user_id)
                                                                                .removeValue()
                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if(task.isSuccessful()){
                                                                                            ChatRequestRef.child(list_user_id).child(currentUserID)
                                                                                                    .removeValue()
                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                            if(task.isSuccessful()){
                                                                                                                Toast.makeText(getContext(), "New contacts", Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        }
                                                                                                    });
                                                                                        }
                                                                                    }
                                                                                });
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        });

                                        // Bắt sự kiện cancel
                                        holder.CancleButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ChatRequestRef.child(currentUserID).child(list_user_id)
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    ChatRequestRef.child(list_user_id).child(currentUserID)
                                                                            .removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if(task.isSuccessful()){
                                                                                        Toast.makeText(getContext(), "Delete request!", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            // Nếu là mình gửi yêu cầu
                            else if(type.equals("sent")){
                                // Nếu gửi yêu cầu thì hiện button bảo hủy yêu cầu request
                                UsersRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild("image")){
                                            String  requestProfileImage = dataSnapshot.child("image").getValue().toString();
                                            Picasso.with(getContext())
                                                    .load(requestProfileImage)
                                                    .into(holder.profileImage);
                                        }
                                        String  requestUserName = dataSnapshot.child("name").getValue().toString();
                                        String  requestUserStatus = "you can cancel chat request";

                                        holder.userName.setText(requestUserName);
                                        holder.userStatus.setText(requestUserStatus);
                                        holder.CancleButton.setVisibility(View.VISIBLE);
                                        holder.AcceptButton.setVisibility(View.INVISIBLE);
                                        holder.CancleButton.setText("Cancel Request");

                                        holder.CancleButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ChatRequestRef.child(currentUserID).child(list_user_id)
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    ChatRequestRef.child(list_user_id).child(currentUserID)
                                                                            .removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if(task.isSuccessful()){
                                                                                        Toast.makeText(getContext(), "Delete request!", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout,parent,false);
                RequestsViewHolder viewHolder = new RequestsViewHolder(view);
                return viewHolder;
            }
        };

        myRequestList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class RequestsViewHolder extends RecyclerView.ViewHolder{
        TextView userName, userStatus;
        CircleImageView profileImage;
        Button AcceptButton, CancleButton;

        public RequestsViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_profile_name);
            userStatus = itemView.findViewById(R.id.user_status);
            profileImage = itemView.findViewById(R.id.users_profile_image);
            AcceptButton = itemView.findViewById(R.id.request_accept_btn);
            CancleButton = itemView.findViewById(R.id.request_cancel_btn);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
