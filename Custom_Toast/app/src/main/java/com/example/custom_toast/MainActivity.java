package com.example.custom_toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Vào layout tạo fiele custom toast
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnToast = findViewById(R.id.buttonToast);
        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Hello Tu Nguyen");
            }
        });

        Button btnPosition = findViewById(R.id.buttonToast2);
        btnPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Position_Toast("Change Position of Toast");
            }
        });
    }
    public void showToast(String text){
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_root));
        // Viết lại text trong toast
        TextView txt = v.findViewById(R.id.TextViewToast);
        txt.setText(text);
        // Dùng icon khác :
        ImageView img = v.findViewById(R.id.imageIcon);
        img.setImageResource(R.drawable.ic_insert_emoticon_black_24dp);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(v);
        toast.show();
    }

    public void Change_Position_Toast(String text){
        Context context;
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.START|Gravity.CENTER_VERTICAL,90,0);
        toast.show();
    }

}
