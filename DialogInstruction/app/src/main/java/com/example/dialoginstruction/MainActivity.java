package com.example.dialoginstruction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Tạo dialog trong suốt ấy mà :))
    Button button;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        displayDialog();
    }

    private void displayDialog() {

        Dialog dialog = new Dialog(this);

        // setView
        dialog.setContentView(R.layout.dialog_instruction);

        // setLayout
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        // set background trong suốt
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // show dialog
        dialog.show();

        // initialize variable
        View view1 = dialog.findViewById(R.id.view1);
        View view2 = dialog.findViewById(R.id.view2);
        View view3 = dialog.findViewById(R.id.view3);
        TextView text_View = dialog.findViewById(R.id.text_view);

        text_View.setText("This is button");

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                text_View.setText("This is text view");
            }
        });

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.VISIBLE);
                text_View.setText("This is image view");
            }
        });

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void init() {
        button      = findViewById(R.id.button);
        textView    = findViewById(R.id.textView);
        imageView   = findViewById(R.id.imageView);
    }

}