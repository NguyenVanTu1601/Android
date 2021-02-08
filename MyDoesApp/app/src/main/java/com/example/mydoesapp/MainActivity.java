package com.example.mydoesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Take a Note:
 * 1. Install RecyclerView
 * 2. Install Firebase
 * 3. Export and Import JSON file
 * 4. Retrieve Data
 * 5. Display them into XML
 * 6. Custom việc đang nhập input ở ô nào thì đổi màu ô đấy thông qua file bginputtask
 */
public class MainActivity extends AppCompatActivity {

    TextView textTitle, textSubtitle, textEnd;
    FloatingActionButton button;
    RecyclerView recyclerView;
    DatabaseReference ref;
    ArrayList<MyDoes> list;
    DoesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTitle = findViewById(R.id.titlePage);
        textSubtitle = findViewById(R.id.subtitlePage);
        textEnd = findViewById(R.id.endPage);
        button = findViewById(R.id.addDoes);
        ref = FirebaseDatabase.getInstance().getReference();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                intent.putExtra("state","new");
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.ourDoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new DoesAdapter(MainActivity.this, list);
        recyclerView.setAdapter(adapter);


        ref.child("MyDoes").orderByKey().limitToLast(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    String key = snapshot.getKey();
                    MyDoes d = (MyDoes) snapshot.getValue(MyDoes.class).withKey(key);
                    list.add(d);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}