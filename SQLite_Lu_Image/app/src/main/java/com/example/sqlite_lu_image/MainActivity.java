package com.example.sqlite_lu_image;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnThem;
    public static Database database;
    ArrayList<Do_Vat> arrayDoVat;
    ListView listView;
    DoVat_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnThem = findViewById(R.id.buttonThem);
        listView = findViewById(R.id.listViewDoVat);
        arrayDoVat = new ArrayList<>();
        adapter = new DoVat_Adapter(this,R.layout.line_dovat,arrayDoVat);
        listView.setAdapter(adapter);

        database = new Database(this,"QuanLy.sqlite",null,1);
        String creatTable = "CREATE TABLE IF NOT EXISTS DoVat(" +
                            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "Ten VARCHAR(200)," +
                            "MoTa VARCHAR(250)," +
                            "Hinh BLOB)";
        database.QueryData(creatTable);

        // getData
        Cursor cursor = database.getData("SELECT * FROM DoVat");
        while (cursor.moveToNext()){
            arrayDoVat.add(new Do_Vat(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                                      cursor.getBlob(3)));

        }
        adapter.notifyDataSetChanged();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ThemDoVat_Activity.class));

            }
        });
    }
}
