package com.example.blogsapp.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.blogsapp.Comment.Comment;
import com.example.blogsapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Comment> listComment;

    public CommentAdapter(Context context, int layout, List<Comment> listComment) {
        this.context = context;
        this.layout = layout;
        this.listComment = listComment;
    }

    @Override
    public int getCount() {
        return listComment.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolderListView{
        CircleImageView imageProfileComment;
        TextView textComment, nameComment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderListView viewHolder;
        if(convertView == null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            // ánh xạ qua view
            viewHolder = new ViewHolderListView();
            viewHolder.imageProfileComment = convertView.findViewById(R.id.item_profile_comment);
            viewHolder.textComment = convertView.findViewById(R.id.item_comment_show);
            viewHolder.nameComment = convertView.findViewById(R.id.item_name_comment);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolderListView) convertView.getTag();
        }

        // gán giá trị
        final Comment comment = listComment.get(position);
        viewHolder.textComment.setText(comment.getComment());
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Users").child(comment.currentID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(context)
                        .load(dataSnapshot.child("image").getValue().toString())
                        .error(R.drawable.profile_image)
                        .into(viewHolder.imageProfileComment);

                viewHolder.nameComment.setText(dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return convertView;

    }
}
