package com.example.jsoup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class English_adapter extends BaseAdapter {
    Context context;
    int layout;
    List<English> listE;

    public English_adapter(Context context, int layout, List<English> listE) {
        this.context = context;
        this.layout = layout;
        this.listE = listE;
    }

    @Override
    public int getCount() {
        return listE.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        ImageView imgHinh;
        TextView txtNoidung;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.imgHinh = convertView.findViewById(R.id.line_image);
            holder.txtNoidung = convertView.findViewById(R.id.line_Content);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        English e = listE.get(position);
        holder.txtNoidung.setText(e.getContent());
        Picasso.with(context)
                .load(e.getImage())
                .into(holder.imgHinh);

        return convertView;
    }
}
