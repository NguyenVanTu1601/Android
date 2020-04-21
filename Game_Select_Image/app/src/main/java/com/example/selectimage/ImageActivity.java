package com.example.selectimage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Collections;

public class ImageActivity extends Activity {
    TableLayout myTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Anhxa();
        int soDong = 5;
        int soCot = 3;
        // trộn mảng
        Collections.shuffle(MainActivity.arrayName);

        // Sinh các ô trong table
        for(int dong = 1; dong <= soDong; dong++){
            TableRow tableRow= new TableRow(this);
            for(int cot = 1; cot <= soCot; cot++){
                // Tạo imageView và set ảnh vao
                final int vitri = soCot * (dong-1) + cot-1;
                ImageView imageView = new ImageView(this);

                // Conver dp-> px
                Resources r = getResources();
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,r.getDisplayMetrics());

                // Tạo layoutParams để set vào imageView
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(px,px); // tạo từng ô có kt 350*300
                imageView.setLayoutParams(layoutParams); // thêm ô vừa tạo vào imagview

                // Thêm ảnh vao imageView
                int idHinh = getResources().getIdentifier(MainActivity.arrayName.get(vitri),"drawable",getPackageName());
                imageView.setImageResource(idHinh);

                // Add ImageView vào TableRow
                tableRow.addView(imageView);

                // bắt sự kiện
                final String s = MainActivity.arrayName.get(vitri);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ImageActivity.this, s, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("tenhinhchon", s);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }

            //Add tableRow vào TableLayout sau khi đủ 1 dòng table
            myTable.addView(tableRow);
        }

    }
    private void Anhxa(){
        myTable = (TableLayout) findViewById(R.id.TableLayoutImage);
    }
}
