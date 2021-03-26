package com.example.wallpaperapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.wallpaperapplication.databinding.ActivityViewWallPaperBinding;

public class ViewWallPaper extends AppCompatActivity {

    ActivityViewWallPaperBinding binding;

    WallpaperManager wallpaperManager;
    DisplayMetrics displayMetrics;
    Bitmap bitmap;
    BitmapDrawable bitmapDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewWallPaperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String image = getIntent().getStringExtra("image");

        binding.imageFull.setImageResource(Integer.parseInt(image));

        binding.setLockScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewWallPaper.this, "Lock screen!", Toast.LENGTH_SHORT).show();
                setWallPaper("Lock");
            }
        });

        binding.setHomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewWallPaper.this, "Home screen!!!", Toast.LENGTH_SHORT).show();
                setWallPaper("Home");
            }
        });
    }
    private void setWallPaper(String type){
        int[] size = getScreenSize();
        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        bitmapDrawable = (BitmapDrawable) binding.imageFull.getDrawable();
        bitmap = bitmapDrawable.getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap, size[0], size[1], false);
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                if (type.equals("Lock")){
                    wallpaperManager.setBitmap(bitmap, null, true,WallpaperManager.FLAG_LOCK);
                }else{
                    if (type.equals("Home")){
                        wallpaperManager.setBitmap(bitmap, null, true,WallpaperManager.FLAG_SYSTEM);
                    }
                }
            }else{
                wallpaperManager.setBitmap(bitmap);
            }
            wallpaperManager.suggestDesiredDimensions(size[0], size[1]);
        }catch(Exception e){

        }

    }

    private int[] getScreenSize() {
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int[] size = new int[2];
        size[0] = displayMetrics.widthPixels;
        size[1] = displayMetrics.heightPixels;
        return size;
    }
}