package com.example.sqlite_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Một số chú ý
 * Do các icon sửa, xóa nằm trong holder nên để bắt sự kiện phải bắt trong adapter chứa holder
 * Các button nằm trong dialog bắt sự kiện trong hàm gọi dialog, tuy nhiên hàm gọi dialog phải trong MainActivity
 * Các dialog chỉ gọi được và tạo hàm bên MainActivity. Để gọi bên adapter thì Context context thay bằng MainActivity context
 * ---sau đó gọi dialong bằng context.tên hàm
 * @param
 */

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView listViewCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViec_Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ánh xạ :
        listViewCongViec = findViewById(R.id.listViewCongViec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViec_Adapter(this, R.layout.dong_cong_viec,arrayCongViec);
        listViewCongViec.setAdapter(adapter);

        // Khởi tạo database
        database = new Database(this,"ghichu.sqlite",null,1);

        // Tạo các truy vấn
        String creatTable   = "CREATE TABLE IF NOT EXISTS CongViec(" +
                              "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                              "TenCV VARCHAR(200))";
        String insertValues = "INSERT INTO CongViec " +
                              "VALUES(null,'Làm bài tập android')," +
                              "(null,'Viết ứng dụng ghi chú')";
        String dataCV       = "SELECT * FROM CongViec";

        // Tạo bảng
        database.QueryData(creatTable);

        // Thêm dữ liệu
        //database.QueryData(insertValues);

        // Đọc dữ liệu
        Cursor data = database.getData(dataCV);
        arrayCongViec.clear(); // xóa hết đọc từ đầu
        while(data.moveToNext()){
            String name = data.getString(1);
            int id = data.getInt(0);
            arrayCongViec.add(new CongViec(id,name));
        }
        adapter.notifyDataSetChanged();
    }

    // Thêm menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Bắt sự kiện chọn item menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuthemcv){
            dialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    // Dialog thêm công việc
    private void dialogThem(){
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false); // click ra ngoai ko tắt
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themcv);


        final EditText edt = dialog.findViewById(R.id.editTextNhap);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);
        Button btnThem = dialog.findViewById(R.id.buttonXacNhan);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCv = edt.getText().toString();
                if(tenCv.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show();
                }else{
                    database.QueryData("INSERT INTO CongViec VALUES(null,'"+ tenCv + "')");
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    // Load lại dữ liệu
                    Cursor data = database.getData("SELECT * FROM CongViec");
                    arrayCongViec.clear(); // xóa hết đọc từ đầu
                    while(data.moveToNext()){
                        String name = data.getString(1);
                        int id = data.getInt(0);
                        arrayCongViec.add(new CongViec(id,name));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        dialog.show();
    }

    // Dialog sửa thông tin
    public void showDiaLogSua(String tenCV, final int id){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suacv);
        final EditText edtcongviec = dialog.findViewById(R.id.editTextSua);
        Button btnXacnhan = dialog.findViewById(R.id.buttonSua);
        Button btnHuy  = dialog.findViewById(R.id.buttonHuy2);

        edtcongviec.setText(tenCV);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtcongviec.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '"+tenMoi+"' WHERE Id = '" + id +"'");
                Toast.makeText(MainActivity.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                // Load lại dữ liệu
                Cursor data = database.getData("SELECT * FROM CongViec");
                arrayCongViec.clear(); // xóa hết đọc từ đầu
                while(data.moveToNext()){
                    String name = data.getString(1);
                    int id = data.getInt(0);
                    arrayCongViec.add(new CongViec(id,name));
                }
                adapter.notifyDataSetChanged();
            }
        });
        dialog.show();
    }

    // Dialog xóa thông tin
    public void showDialogXoa(String tenCv, final int id){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setTitle("Xóa công việc");
        dialogDelete.setMessage("Bạn có muốn xóa công việc " + tenCv +  " không?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+ id + "'");
                Toast.makeText(MainActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();

                // Load lại dữ liệu
                Cursor data = database.getData("SELECT * FROM CongViec");
                arrayCongViec.clear(); // xóa hết đọc từ đầu
                while(data.moveToNext()){
                    String name = data.getString(1);
                    int id = data.getInt(0);
                    arrayCongViec.add(new CongViec(id,name));
                }
                adapter.notifyDataSetChanged();
            }
        });

        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }
}
