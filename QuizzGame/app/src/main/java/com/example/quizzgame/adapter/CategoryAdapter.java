package com.example.quizzgame.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.quizzgame.QuizActivity;
import com.example.quizzgame.R;
import com.example.quizzgame.model.CategoryModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<CategoryModel> categories;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel categoryModel = categories.get(position);

        holder.textNameCategory.setText(categoryModel.getCategoryName());
        Glide.with(context).load(categoryModel.getCategoryImage())
                .into(holder.imageCategory);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clickable!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra("catId", categoryModel.getCategoryId());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // download ảnh về máy , test thử cho vui thôi vì tiện
                Glide.with(context).load(categoryModel.getCategoryImage())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Dexter.withContext(context)
                                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        .withListener(new PermissionListener() {
                                            @Override
                                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                                downLoadFromImageView(holder.imageCategory);
                                            }

                                            @Override
                                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                                Toast.makeText(context, "Need permission to download and save image", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                                            }
                                        }).check();

                                return false;
                            }
                        })
                        .into(holder.imageCategory);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView imageCategory;
        TextView textNameCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCategory   = itemView.findViewById(R.id.item_image_category);
            textNameCategory= itemView.findViewById(R.id.item_name_category);
        }
    }

    private void downLoadFromImageView(ImageView imageView){
        Calendar c = Calendar.getInstance();
        String time = c.getTime().toString();
        String imageFileName = time;
        FileOutputStream fileOutputStream;
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"IMAGE_FIREBASE");
        if (!file.exists() && !file.mkdir()){
            // mkdir : kiểm tra có cho phép tạo thư mục ko
            Toast.makeText(context, "Not Create File!!!", Toast.LENGTH_SHORT).show();
        }else{
            File fileName = new File(file.getParentFile() + "/" + imageFileName);
            try{
                fileOutputStream = new FileOutputStream(fileName);
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                Toast.makeText(context, "Save image success!!!", Toast.LENGTH_SHORT).show();
                fileOutputStream.flush();
                fileOutputStream.close();
            }catch (Exception e){
                Toast.makeText(context,"Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }


    }

}
