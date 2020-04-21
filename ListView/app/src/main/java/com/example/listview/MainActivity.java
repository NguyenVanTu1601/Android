package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    Button buttonThem, buttonSua, buttonXoa;
    EditText editText;
    int vitri = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView =(ListView) findViewById(R.id.listViewMonHoc);
        arrayList.add("Android");
        arrayList.add("IOS");
        arrayList.add("Java");
        arrayList.add("C++");
        arrayList.add("PHP");
        final ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,
                                                android.R.layout.simple_expandable_list_item_1,
                                                arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // biến i trả về vị trí item trên listView , bắt đầu từ 0
                Toast.makeText(MainActivity.this, ""+i, Toast.LENGTH_SHORT).show();
            }
        });
        editText = (EditText) findViewById(R.id.editTextMonHoc);
        buttonThem = (Button) findViewById(R.id.buttonThem);
        buttonSua = (Button) findViewById(R.id.buttonSua);
        buttonXoa = (Button) findViewById(R.id.buttonXoa);
        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                arrayList.add(s);
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
        // Cập nhật lại danh sách sau khi xóa hoặc sửa
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(arrayList.get(i));
                vitri = i;
            }
        });
        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.set(vitri, editText.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
        buttonXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(vitri);
                adapter.notifyDataSetChanged();
                editText.setText("");

            }
        });
    }
}
