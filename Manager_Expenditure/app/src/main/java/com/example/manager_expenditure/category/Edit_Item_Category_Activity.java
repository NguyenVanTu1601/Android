package com.example.manager_expenditure.category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.manager_expenditure.R;

public class Edit_Item_Category_Activity extends AppCompatActivity {

    private ImageButton imgClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__item__category_);

        imgClose = findViewById(R.id.close_edit_item_category);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}