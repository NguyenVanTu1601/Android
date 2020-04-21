package com.example.firebase_storage_database_image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Image_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Image> imageList;

    public Image_adapter() {
    }

    public Image_adapter(Context context, int layout, List<Image> imageList) {
        this.context = context;
        this.layout = layout;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
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
        TextView txtTen;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder.imgHinh = convertView.findViewById(R.id.imageViewLineImage);
            viewHolder.txtTen = convertView.findViewById(R.id.textViewLineTen);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Image image = imageList.get(position);
        viewHolder.txtTen.setText(image.getTenHinh().toString());
        Picasso.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/fir-storagedatabase-" +
                        "2f476.appspot.com/o/image1584505869158.png?alt=media&token=104dd7f3-" +
                        "e8d0-4e0b-84b9-5c6b97a43934") // bình thường là image.getLinkHinh() nhưng ko tìm được link gửi lên
                .into(viewHolder.imgHinh);
        return convertView;
    }
}
