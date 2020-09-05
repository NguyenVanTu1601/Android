package com.example.display_photo_from_gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHoder> {

    private Context context;
    private List<String> listImage;
    protected PhotoListener photoListener;

    public GalleryAdapter(Context context, List<String> listImage, PhotoListener photoListener) {
        this.context = context;
        this.listImage = listImage;
        this.photoListener = photoListener;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(
                LayoutInflater.from(context).inflate(R.layout.gallery_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoder holder, int position) {
        final String image = listImage.get(position);
        Glide.with(context).load(image).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // image này là đường dẫn tới ảnh
                photoListener.onPhotoClick(image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    public interface PhotoListener{
        void onPhotoClick(String path);
    }
}
