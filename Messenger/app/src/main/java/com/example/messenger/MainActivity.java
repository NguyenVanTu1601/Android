package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtMessenger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        initView();
    }
    private void Anhxa(){
        edtMessenger = findViewById(R.id.EditTextMessenger);

    }

    public void initView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewChat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<DataShop> dataShops = new ArrayList<>();
        dataShops.add(new DataShop(R.drawable.ic_launcher_foreground,"Hihi"));
        dataShops.add(new DataShop(R.drawable.ic_launcher_foreground,"Haha"));
        dataShops.add(new DataShop(R.drawable.ic_launcher_foreground,"Hehe"));
        dataShops.add(new DataShop(R.drawable.ic_launcher_foreground,"Huhu"));
        Shop_Adapter shop_adapter = new Shop_Adapter(dataShops,getApplicationContext() );

        recyclerView.setAdapter(shop_adapter);
    }

}
