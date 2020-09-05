package com.example.test_listview_with_editext.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.test_listview_with_editext.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText edtSearch;
    private RecyclerView recyclerView;
    private TextView textTongtien,textTongcan;
    private ImageButton imgAdd;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void initialization(){
        edtSearch = findViewById(R.id.inputSearch);
        recyclerView = findViewById(R.id.noteRecyclerView);
        textTongtien = findViewById(R.id.Tongtien);
        textTongcan = findViewById(R.id.Socan);
        imgAdd = findViewById(R.id.buttonAdd);
    }

    private void showDialog(){
        if(alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_new,
                    (ViewGroup) findViewById(R.id.layoutAddUrlContainer), false);
            builder.setView(view);
            alertDialog = builder.create();
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            EditText ngayboc, socan, dongia;

            ngayboc = view.findViewById(R.id.inputTime);
            socan = view.findViewById(R.id.inputNumber);
            dongia = view.findViewById(R.id.inputDongia);

            ngayboc.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(new Date()));
            dongia.setText("4000");

            view.findViewById(R.id.textAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
        alertDialog.show();
    }
}
