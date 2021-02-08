package com.example.mydoesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewTaskActivity extends AppCompatActivity {

    private EditText edtTitle, edtDescription, edtDate;
    private Button btnCreate, btnCancle;
    private TextView textTitle;
    DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        edtTitle = findViewById(R.id.addTitleDoes);
        edtDescription = findViewById(R.id.addDescDoes);
        edtDate = findViewById(R.id.addDateDoes);
        btnCreate = findViewById(R.id.btnSaveTask);
        btnCancle = findViewById(R.id.btnCancle);
        textTitle = findViewById(R.id.titlePage);
        String state = getIntent().getStringExtra("state");

        rootRef = FirebaseDatabase.getInstance().getReference();
        final String keyDoes = rootRef.child("MyDoes").push().getKey();

        if(state.equals("new")){

            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = edtTitle.getText().toString();
                    String desc = edtDescription.getText().toString();
                    String date = edtDate.getText().toString();

                    Map<String, Object> mapDoes = new HashMap<>();
                    mapDoes.put("titledoes",title);
                    mapDoes.put("descdoes",desc);
                    mapDoes.put("datedoes",date);
                    rootRef.child("MyDoes").child(keyDoes).updateChildren(mapDoes)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(NewTaskActivity.this, "Thêm thành công...", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(NewTaskActivity.this, MainActivity.class));
                                    finish();
                                }
                            });
                }
            });
        }else if(state.equals("edit")){

            textTitle.setText("Edit Does");
            btnCreate.setText("Save Does");

            edtTitle.setText(getIntent().getStringExtra("title"));
            edtDescription.setText(getIntent().getStringExtra("desc"));
            edtDate.setText(getIntent().getStringExtra("date"));
            final String key = getIntent().getStringExtra("key");

            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String title = edtTitle.getText().toString();
                    String desc = edtDescription.getText().toString();
                    String date = edtDate.getText().toString();

                    Map<String, Object> mapDoes = new HashMap<>();
                    mapDoes.put("titledoes",title);
                    mapDoes.put("descdoes",desc);
                    mapDoes.put("datedoes",date);

                    rootRef.child("MyDoes").child(key).updateChildren(mapDoes).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(NewTaskActivity.this, "Edit thành công...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NewTaskActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                }
            });

        }else{
            startActivity(new Intent(NewTaskActivity.this, MainActivity.class));
            finish();
        }


        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewTaskActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}