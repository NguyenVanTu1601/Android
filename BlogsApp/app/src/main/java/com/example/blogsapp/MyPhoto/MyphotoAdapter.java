package com.example.blogsapp.MyPhoto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blogsapp.R;

import java.io.Serializable;
import java.util.List;

public class MyphotoAdapter extends RecyclerView.Adapter<MyphotoAdapter.ViewHolderMyPhoto> {

    private Context context;
    private List<String> listPhoto;

    public MyphotoAdapter(Context context, List<String> listPhoto) {
        this.context = context;
        this.listPhoto = listPhoto;
    }



    @NonNull
    @Override
    public ViewHolderMyPhoto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderMyPhoto(
                LayoutInflater.from(context).inflate(R.layout.item_myphoto,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMyPhoto holder, int position) {
        final String image = listPhoto.get(position);
        Glide.with(context).load(image).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FullScreenActivity.class);
                intent.putExtra("image", (Serializable) listPhoto);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPhoto.size();
    }

    public class ViewHolderMyPhoto extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolderMyPhoto(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_myphoto);
        }
    }
}
