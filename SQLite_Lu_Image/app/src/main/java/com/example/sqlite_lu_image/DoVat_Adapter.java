package com.example.sqlite_lu_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DoVat_Adapter extends BaseAdapter {
    private Context context; // bối cảnh gọi
    private int layout;     // layout
    private List<Do_Vat> listDoVat;

    public DoVat_Adapter(Context context, int layout, List<Do_Vat> listDoVat) {
        this.context = context;
        this.layout = layout;
        this.listDoVat = listDoVat;
    }

    @Override
    public int getCount() {
        return listDoVat.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView txtTen, txtMoTa;
        ImageView imgHinh;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            //Anh xạ view holder
            viewHolder.txtTen = convertView.findViewById(R.id.textViewTenCustom);
            viewHolder.txtMoTa = convertView.findViewById(R.id.textViewMoTaCustom);
            viewHolder.imgHinh = convertView.findViewById(R.id.imageViewCusTom);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Do_Vat do_vat = listDoVat.get(position);
        viewHolder.txtTen.setText(do_vat.getTen().toString());
        viewHolder.txtMoTa.setText(do_vat.getMota());
        // Chuyển hình từ byte -> bitmap
        byte[] hinhanh = do_vat.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);

        viewHolder.imgHinh.setImageBitmap(bitmap);

        return convertView;
    }
}
