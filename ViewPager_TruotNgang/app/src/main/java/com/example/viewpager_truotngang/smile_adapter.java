package com.example.viewpager_truotngang;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class smile_adapter extends PagerAdapter {
    List<Face> list;
    LayoutInflater inflate;

    public smile_adapter(List<Face> list, Context context) {
        this.list = list;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflate.inflate(R.layout.item_smile,container,false);
        // Anh xạ :
        // Anh xạ layout để bắt màu
        //ConstraintLayout layout = view.findViewById(R.id.layout_smile);
        //ImageView imgHinh = view.findViewById(R.id.imageViewHinh);
        TextView txtTen = view.findViewById(R.id.textViewTen);
        TextView txtMau = view.findViewById(R.id.textView);
        TextView txtHinh = view.findViewById(R.id.textView2);
        // Thêm màu
        Face face = list.get(position);

        //layout.setBackgroundColor(Color.parseColor(face.getmColor()));
        //imgHinh.setImageResource(R.drawable.images);
        txtTen.setText(face.getName());
        txtMau.setText(face.getmColor());
        txtHinh.setText(face.getFaceId() + "");
        // add view
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
