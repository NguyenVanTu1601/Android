package com.example.blogsapp.Comment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.blogsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {

    private ListView listViewComment;
    private EditText textComment;
    private Button buttonComment;
    private LinearLayout layoutComment;
    private List<Comment> listCommentItem;
    private CommentAdapter commentAdapter;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        listViewComment = findViewById(R.id.listViewComment);
        textComment = findViewById(R.id.textComment);
        buttonComment = findViewById(R.id.buttonComment);
        layoutComment = findViewById(R.id.layoutComment);

        listCommentItem = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, R.layout.list_item_comment, listCommentItem);
        listViewComment.setAdapter(commentAdapter);
        rootRef = FirebaseDatabase.getInstance().getReference();

        final String key = getIntent().getStringExtra("key"); // key của blog đang xem
        final String currentId = getIntent().getStringExtra("currentId"); // id người dùng hiện tại
        final String uid = getIntent().getStringExtra("uid"); // ID người viết blog;

        rootRef.child("Blog").child(key).child("Comment")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.exists()) {

                            Comment comment = dataSnapshot.getValue(Comment.class);
                            listCommentItem.add(comment);
                            commentAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textCmt = textComment.getText().toString();
                final String dateTime = new SimpleDateFormat("EEEE, dd MMMM yyyy HH : mm a",
                        Locale.getDefault()).format(new Date());
                if (!textCmt.isEmpty()) {
                    Map<String, Object> mapComment = new HashMap<>();
                    mapComment.put("currentID", currentId);
                    mapComment.put("comment", textCmt);
                    mapComment.put("datetime", dateTime );
                    rootRef.child("Blog").child(key).child("Comment").push()
                            .updateChildren(mapComment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                commentAdapter.notifyDataSetChanged();
                                textComment.setText("");

                                // Thêm vào notification
                                // currentid -> key blog -> like / comment
                                Map<String, Object> mapNotification = new HashMap<>();
                                mapNotification.put("time", dateTime); // id người like
                                mapNotification.put("id",currentId);
                                mapNotification.put("idBlog", key);

                                if(!uid.equals(currentId)){
                                    rootRef.child("Notification").child(uid).child("Comments").child(currentId).updateChildren(mapNotification)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                }
                            }
                        }
                    });



                }
            }
        });
    }
}