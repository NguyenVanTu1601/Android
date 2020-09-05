package com.example.blogsapp.MyPhoto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.blogsapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<String> listSliderItem;
    private Context context;
    private ViewPager2 viewPager2;

    public SliderAdapter(List<String> listSliderItem, Context context, ViewPager2 viewPager2) {
        this.listSliderItem = listSliderItem;
        this.context = context;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slide_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImageView(listSliderItem.get(position));
        if(position == listSliderItem.size() - 2){
            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return listSliderItem.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlider);
        }

        void setImageView(String sliderItem){
            Glide.with(context).load(sliderItem).into(imageView);
        }
    }

    //Làm mới và lặp lại các ảnh
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            listSliderItem.addAll(listSliderItem);
            notifyDataSetChanged();
        }
    };
}
