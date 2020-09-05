package com.example.dialog_alert;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anh_xa();
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, arrayList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Xacnhanxoa(i);
            }
        });
    }
    private void Xacnhanxoa(final int vitri){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo!");
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setMessage("Xác nhận xóa tên của " + arrayList.get(vitri));
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                arrayList.remove(vitri);
                adapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.show();
    }
    private void Anh_xa(){
        lv = (ListView) findViewById(R.id.listView);
        arrayList.add("Tú");
        arrayList.add("Tùng");
        arrayList.add("Trung");
        arrayList.add("Nam");
        arrayList.add("Việt");
    }

}
