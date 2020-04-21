package com.example.sqlite_database;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CongViec_Adapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> list;

    public CongViec_Adapter(MainActivity context, int layout, List<CongViec> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        TextView txtTen;
        ImageView imgDelete, imgSua;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.txtTen = convertView.findViewById(R.id.tenCV);
            holder.imgSua = convertView.findViewById(R.id.sua);
            holder.imgDelete = convertView.findViewById(R.id.xoa);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final CongViec congviec = list.get(position);
        holder.txtTen.setText(congviec.getTenCv());

        // Bắt sự kiện xóa và sửa
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showDialogXoa(congviec.getTenCv(),congviec.getId());
            }
        });

        // Sửa
        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showDiaLogSua(congviec.getTenCv(),congviec.getId());
                
            }
        });

        return convertView;
    }
}
