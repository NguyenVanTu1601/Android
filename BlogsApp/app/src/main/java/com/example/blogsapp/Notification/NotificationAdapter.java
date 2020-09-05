package com.example.blogsapp.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.blogsapp.Notification.Notification;
import com.example.blogsapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Notification> listNotification;

    public NotificationAdapter(Context context, int layout, List<Notification> listNotification) {
        this.context = context;
        this.layout = layout;
        this.listNotification = listNotification;
    }

    @Override
    public int getCount() {
        return listNotification.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolderNotification{
        TextView textName;
        TextView textTime;
        CircleImageView imageNotification;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderNotification viewHolder;
        if(convertView == null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            // ánh xạ qua view
            viewHolder = new ViewHolderNotification();
            viewHolder.textName = convertView.findViewById(R.id.item_name_notification);
            viewHolder.textTime = convertView.findViewById(R.id.item_time_notification);
            viewHolder.imageNotification = convertView.findViewById(R.id.item_profile_notification);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolderNotification) convertView.getTag();
        }

        // gán giá trị
        final Notification notification = listNotification.get(position);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Users").child(notification.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(context)
                        .load(dataSnapshot.child("image").getValue().toString())
                        .error(R.drawable.profile_image)
                        .into(viewHolder.imageNotification);

                viewHolder.textName.setText(dataSnapshot.child("name").getValue().toString() + " đã "
                + notification.loaiNotification + " bài viết của bạn");

                viewHolder.textTime.setText(notification.getTime());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return convertView;
    }
}
