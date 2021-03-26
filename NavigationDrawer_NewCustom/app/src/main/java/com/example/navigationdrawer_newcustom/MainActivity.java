package com.example.navigationdrawer_newcustom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layoutContent;
    LinearLayout layoutNavigation;
    Animation fromtop, frombottom;
    CardView userImage, imageContent;
    TextView txtUserName, txtCountry, txtExplore, txtAddOne, txtMessage, txtSetting, txtSignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromtop    = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        imageContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutContent.animate().translationX(0);
                layoutNavigation.animate().translationX(0);

                userImage.startAnimation(fromtop);
                txtUserName.startAnimation(fromtop);
                txtCountry.startAnimation(fromtop);

                txtExplore.startAnimation(frombottom);
                txtAddOne.startAnimation(frombottom);
                txtMessage.startAnimation(frombottom);
                txtSetting.startAnimation(frombottom);
                txtSignOut.startAnimation(frombottom);
            }
        });

        layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutContent.animate().translationX(-585);
                layoutNavigation.animate().translationX(-585);
            }
        });
    }

    private void init() {
        imageContent        = findViewById(R.id.user_image_home);
        layoutContent       = findViewById(R.id.layoutContent);
        layoutNavigation    = findViewById(R.id.layoutNavigation);
        userImage           = findViewById(R.id.userimage);

        txtUserName         = findViewById(R.id.username);
        txtCountry          = findViewById(R.id.country);
        txtExplore          = findViewById(R.id.explore);
        txtAddOne           = findViewById(R.id.addones);
        txtMessage          = findViewById(R.id.messages);
        txtSetting          = findViewById(R.id.settings);
        txtSignOut          = findViewById(R.id.signout);
    }
}