package com.example.dictionary_it;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    English_Adapter adapter;
    ArrayList<English> listDictionary;
    Database database;
    int dem = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listDictionary = new ArrayList<>();
        listView = findViewById(R.id.listView);
        database = new Database(this,"Dictionary.sqlite",null,1);
        CreateDatabase();
        LoadData();
        adapter = new English_Adapter(this, R.layout.line_english,listDictionary);
        listView.setAdapter(adapter);


        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.QueryData("INSERT INTO TuVung VALUES('Hello" + dem + "'" + ",'Xin chào')");
                dem++;
                Cursor data = database.getData("SELECT * FROM TuVung");
                listDictionary.clear(); // xóa hết đọc từ đầu
                while(data.moveToNext()){
                    String nghiacuatu = data.getString(1);
                    String tuvung = data.getString(0);
                    listDictionary.add(new English(tuvung,nghiacuatu));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void CreateDatabase(){
        String createTable   = "CREATE TABLE IF NOT EXISTS TuVung(" +
                                "Word VARCHAR(200) PRIMARY KEY ," +
                                "Mean VARCHAR(200))";

        database.QueryData(createTable);
    }

    private void LoadData(){
        Cursor data = database.getData("SELECT * FROM TuVung");
        if(data.getCount() != 0){
            listDictionary.clear(); // xóa hết đọc từ đầu
            while(data.moveToNext()){
                String nghiacuatu = data.getString(1);
                String tuvung = data.getString(0);
                listDictionary.add(new English(tuvung,nghiacuatu));
            }
            //adapter.notifyDataSetChanged();
            // Dòng này bị xóa do mới đầu khi khởi động máy chỉ đọc list vào và chưa tồn tại adaptor nên ko cần notifysetChange

        }else{
            data.close();
        }
    }
}
