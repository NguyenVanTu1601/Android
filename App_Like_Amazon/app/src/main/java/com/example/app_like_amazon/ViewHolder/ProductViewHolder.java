package com.example.app_like_amazon.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_like_amazon.Interface.ItemClickListener;
import com.example.app_like_amazon.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtproductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listener;
    public ProductViewHolder(View itemView){
        super(itemView);

        imageView = itemView.findViewById(R.id.product_item_image);
        txtproductName = itemView.findViewById(R.id.product_item_name);
        txtProductDescription = itemView.findViewById(R.id.product_item_description);
        txtProductPrice = itemView.findViewById(R.id.product_item_price);

    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(),false);
    }
}
