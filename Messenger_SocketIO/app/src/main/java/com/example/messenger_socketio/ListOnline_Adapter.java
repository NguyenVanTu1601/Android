package com.example.messenger_socketio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListOnline_Adapter extends RecyclerView.Adapter<ListOnline_Adapter.ViewHolder> {
    Context context;
    int layout;
    List<User> listOnline;

    public ListOnline_Adapter(Context context, int layout, List<User> listOnline) {
        this.context = context;
        this.layout = layout;
        this.listOnline = listOnline;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = listOnline.get(position);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(user.getAvata(), 0, user.getAvata().length);
        holder.txtTen.setText(user.getTenNguoiDung());
        //holder.imgAvata.setImageBitmap(bitmap);
    }


    @Override
    public int getItemCount() {
        return listOnline.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAvata;
        TextView txtTen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvata = itemView.findViewById(R.id.lineImageAvata);
            txtTen = itemView.findViewById(R.id.lineTextName);
        }

    }
}
