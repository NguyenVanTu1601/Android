package com.example.read_danh_ba_dt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Contact_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Contact> listDanhba;

    public Contact_adapter(Context context, int layout, List<Contact> listDanhba) {
        this.context = context;
        this.layout = layout;
        this.listDanhba = listDanhba;
    }

    @Override
    public int getCount() {
        return listDanhba.size();
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
        ImageView imgHinh;
        TextView txtTen, txtPhone;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            // Ánh xạ
            holder.imgHinh = convertView.findViewById(R.id.imageViewLineHinh);
            holder.txtPhone = convertView.findViewById(R.id.textViewLineSDT);
            holder.txtTen = convertView.findViewById(R.id.textViewLineTen);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = listDanhba.get(position);
        holder.txtTen.setText(contact.getHoTen());
        holder.txtPhone.setText(contact.getSoDT());
        if(contact.getAvata() == null){
            holder.imgHinh.setImageResource(R.drawable.icon);
        }else{
            holder.imgHinh.setImageBitmap(contact.getAvata());
        }
        return convertView;
    }
}
