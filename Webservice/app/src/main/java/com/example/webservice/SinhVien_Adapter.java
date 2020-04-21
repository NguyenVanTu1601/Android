package com.example.webservice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
;

public class SinhVien_Adapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<SinhVien> sinhViens;

    public SinhVien_Adapter(MainActivity context, int layout, List<SinhVien> sinhViens) {
        this.context = context;
        this.layout = layout;
        this.sinhViens = sinhViens;
    }

    @Override
    public int getCount() {
        return sinhViens.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView txtHoTen, txtDiaChi, txtNamSinh;
        ImageButton btnSua, btnXoa;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            // Gọi layout hiển thị cho view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            viewHolder.txtDiaChi = convertView.findViewById(R.id.textViewlineDiaChi);
            viewHolder.txtHoTen = convertView.findViewById(R.id.textViewlineHoten);
            viewHolder.txtNamSinh = convertView.findViewById(R.id.textViewlineNamSinh);
            viewHolder.btnSua = convertView.findViewById(R.id.imageButtonSua);
            viewHolder.btnXoa = convertView.findViewById(R.id.imageButtonXoa);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final SinhVien sinhVien = sinhViens.get(position);
        viewHolder.txtHoTen.setText(sinhVien.getHoten());
        viewHolder.txtNamSinh.setText("Năm sinh : " + sinhVien.getNamsinh());
        viewHolder.txtDiaChi.setText(sinhVien.getDiachi());

        // Bắt sự kiện xóa sửa 1 line
        viewHolder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Update_Sinhvien_Activiy.class);
                intent.putExtra("dataSinhvien", sinhVien); // class sinh vien phải implement Seriazable
                context.startActivity(intent);
            }
        });

        viewHolder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanXoa(sinhVien.getHoten(), sinhVien.getId());
            }
        });
        return convertView;
    }

    private void XacNhanXoa(String name, final int id){

        // Hàm delete có thể viết ở đây. Tuy nhiên do xóa xong cần update lại thông tin trên listView nên sẽ gọi nó ở Mainacctivity do
        // có hàm getData bên đó
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Bạn muốn xóa sinh viên " + name + " không ?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteStudent(id);
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    /*
    private void DeleteStudent(final int id){
        String urlDelete = "http://192.168.1.5/android_webservice/deleteData.php";
        RequestQueue queue = Volley.newRequestQueue(context); // để gọi context này thì context trên cùng chưa sửa thành mainactivity
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success!")){
                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idSV",String.valueOf(id));
                return params;
            }
        };
        queue.add(stringRequest);
    }

     */
}
