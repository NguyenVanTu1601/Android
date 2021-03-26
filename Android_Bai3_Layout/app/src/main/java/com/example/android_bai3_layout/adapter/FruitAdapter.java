package com.example.android_bai3_layout.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.android_bai3_layout.R;
import com.example.android_bai3_layout.model.Fruit;

import java.io.FileReader;
import java.util.List;

public class FruitAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Fruit> fruits;

    public FruitAdapter(Context context, int layout, List<Fruit> fruits) {
        this.context = context;
        this.layout = layout;
        this.fruits = fruits;
    }

    @Override
    public int getCount() {
        return fruits.size();
    }

    @Override
    public Object getItem(int position) {
        return fruits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        private ImageView imgFruit;
        private TextView textEng, textVN;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            // ánh xạ view
            holder = new ViewHolder();
            holder.imgFruit     = convertView.findViewById(R.id.item_image);
            holder.textVN       = convertView.findViewById(R.id.item_vietnamese);
            holder.textEng      = convertView.findViewById(R.id.item_english);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // gán giá trị
        Fruit fruit = fruits.get(position);
        holder.imgFruit.setImageResource(fruit.getImage());
        holder.textVN.setText(fruit.getVietnamese());
        holder.textEng.setText(fruit.getEnglish());
        holder.imgFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, fruit.getVietnamese(), Toast.LENGTH_SHORT).show();
            }
        });
        

        return convertView;
    }
}
