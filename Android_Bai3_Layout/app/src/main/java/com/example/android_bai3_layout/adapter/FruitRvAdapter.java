package com.example.android_bai3_layout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_bai3_layout.R;
import com.example.android_bai3_layout.model.Fruit;

import java.util.List;

public class FruitRvAdapter extends RecyclerView.Adapter<FruitRvAdapter.FruitViewHolder> {

    private List<Fruit> fruits;
    private Context context;

    public FruitRvAdapter(List<Fruit> fruits, Context context) {
        this.fruits = fruits;
        this.context = context;
    }

    @NonNull
    @Override
    public FruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item,parent,false);
        return new FruitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitViewHolder holder, int position) {
        Fruit fruit = fruits.get(position);
        holder.imgFruit.setImageResource(fruit.getImage());
        holder.fruit_vn.setText(fruit.getVietnamese());
        holder.fruit_eng.setText(fruit.getEnglish());

        holder.imgFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "English : " + fruit.getEnglish(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    // tạo đầu tiên để sinh ra FruitViewHolder onCreateViewHolder
    public class FruitViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgFruit;
        private TextView fruit_eng;
        private TextView fruit_vn;

        public FruitViewHolder(@NonNull View itemView) {
            super(itemView);

            // itemview được truyền từ layout R.layout.list_item trong FruitViewHolder oncreatView
            // Do đó ánh xạ các item này chính là ánh xạ từ list_item
            imgFruit    = itemView.findViewById(R.id.item_image);
            fruit_eng   = itemView.findViewById(R.id.item_english);
            fruit_vn    = itemView.findViewById(R.id.item_vietnamese);
        }
    }
}
