package com.example.android_b07_test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.android_b07_test.R;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private List<Integer> listImage;
    private int layout;
    private Context context;

    public ImageAdapter(List<Integer> listImage, int layout, Context context) {
        this.listImage = listImage;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public Object getItem(int i) {
        return listImage.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder{
        ImageView imageView;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.imageView = view.findViewById(R.id.image_item);
            view.setTag(holder);

        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.imageView.setImageResource(listImage.get(i));
        return view;
    }
}
