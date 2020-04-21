package com.example.shared_animation_between_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    ImageView ImageProfile;
    TextView txtName, txtDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.LinearLayout_information);
        txtName = findViewById(R.id.profile_name);
        txtDesc = findViewById(R.id.profile_desc);
        ImageProfile = findViewById(R.id.profile_image);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Profile_Activity.class);
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View,String>(ImageProfile,"imageTransition");
                pairs[1] = new Pair<View,String>(txtName,"nameTransition");
                pairs[2] = new Pair<View,String>(txtDesc,"descTransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);

                startActivity(intent,options.toBundle());

            }
        });
    }
}
