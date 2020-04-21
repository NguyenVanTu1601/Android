package com.example.dictionary_it;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class English_Adapter extends BaseAdapter {
    Context context;
    int layout;
    List<English> englishList;

    public English_Adapter(Context context, int layout, List<English> englishList) {
        this.context = context;
        this.layout = layout;
        this.englishList = englishList;
    }

    @Override
    public int getCount() {
        return englishList.size();
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
        ImageButton imgVolumn;
        TextView txtTu, txtNghia;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            viewHolder.txtNghia = convertView.findViewById(R.id.line_Nghiacuatu);
            viewHolder.txtTu = convertView.findViewById(R.id.line_TuVung);
            viewHolder.imgVolumn = convertView.findViewById(R.id.line_volumn);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(englishList.isEmpty() == false){
            English e = englishList.get(position);
            viewHolder.txtTu.setText(e.getWord());
            viewHolder.txtNghia.setText(e.getMean());
            viewHolder.imgVolumn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Phát âm", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return convertView;
    }
}
