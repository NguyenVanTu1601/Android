package com.example.chat_firebase;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestFragment_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ContactsRequest> listContactsRequest;
    private DatabaseReference ContactsRef, ChatRequestRef;
    private FirebaseAuth mAuth;
    private String currentID;

    public RequestFragment_Adapter(Context context, int layout, List<ContactsRequest> listContactsRequest) {
        this.context = context;
        this.layout = layout;
        this.listContactsRequest = listContactsRequest;
        ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        ChatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        mAuth = FirebaseAuth.getInstance();
        currentID = mAuth.getCurrentUser().getUid();
    }

    @Override
    public int getCount() {
        return listContactsRequest.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView userName, userStatus;
        CircleImageView profileImage;
        Button AcceptButton, CancleButton;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.userName = convertView.findViewById(R.id.user_profile_name);
            viewHolder.userStatus = convertView.findViewById(R.id.user_status);
            viewHolder.profileImage = convertView.findViewById(R.id.users_profile_image);
            viewHolder.AcceptButton = convertView.findViewById(R.id.request_accept_btn);
            viewHolder.CancleButton = convertView.findViewById(R.id.request_cancel_btn);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final ContactsRequest contacts = listContactsRequest.get(position);
        viewHolder.userName.setText(contacts.getName());
        viewHolder.userStatus.setText(contacts.getStatus());
        viewHolder.AcceptButton.setVisibility(View.VISIBLE);
        viewHolder.CancleButton.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(contacts.getImage())){
            Picasso.with(context).load(contacts.getImage()).placeholder(R.drawable.profile_image)
                    .into(viewHolder.profileImage);
        }
        else{
            viewHolder.profileImage.setImageResource(R.drawable.profile_image);
        }


        viewHolder.AcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thêm nhánh contacts và xóa nhánh ChatRequest
                ContactsRef.child(currentID).child(contacts.getUid()).child("Contacts")
                        .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            ContactsRef.child(contacts.getUid()).child(currentID).child("Contacts")
                                    .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        ChatRequestRef.child(currentID).child(contacts.getUid())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            ChatRequestRef.child(contacts.getUid()).child(currentID)
                                                                    .removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()){
                                                                                Toast.makeText(context, "New Contacts", Toast.LENGTH_SHORT).show();
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

        viewHolder.CancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatRequestRef.child(currentID).child(contacts.getUid())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    ChatRequestRef.child(contacts.getUid()).child(currentID)
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(context, "Delete request!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        });

        return convertView;
    }
}
