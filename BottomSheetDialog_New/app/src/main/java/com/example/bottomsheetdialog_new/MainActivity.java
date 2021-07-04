package com.example.bottomsheetdialog_new;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bottomsheetdialog_new.databinding.ActivityMainBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * Có thể thêm viewPager vào bottom sheet. kiểu fragment trong bottomsheet
 * Tuy nhiên phần xử lý và thêm viewPager để trong onCreate và phần tạo background trong suốt thì nên để ở onCreateDialog
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this,
                        R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_dialog,
                        findViewById(R.id.layout_root), false);

                bottomSheetView.findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Share....", Toast.LENGTH_SHORT).show();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

    }


}