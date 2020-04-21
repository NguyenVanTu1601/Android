package com.example.fragment_truyen_nhan_dulieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button buttonAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAdd = findViewById(R.id.buttonAdd);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment();
                // Truyền dữ liệu
                Bundle bundle = new Bundle();
                bundle.putString("hoten", "Nguyễn Văn Tú");
                fragment.setArguments(bundle);

                //
                fragmentTransaction.add(R.id.FrameLayout,fragment);
                fragmentTransaction.commit();
            }
        });
    }
}
