package com.example.messenger_socketio;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListMessenger_Adapter extends RecyclerView.Adapter<ListMessenger_Adapter.ViewHolder> {
    Context context;
    int layout;
    List<String> listMessenger;

    public ListMessenger_Adapter(Context context, int layout, List<String> listMessenger) {
        this.context = context;
        this.layout = layout;
        this.listMessenger = listMessenger;
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
        holder.txtMessenger.setText(listMessenger.get(position));
    }


    @Override
    public int getItemCount() {
        return listMessenger.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAvata;
        TextView txtTen, txtMessenger;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvata = itemView.findViewById(R.id.lineMessengerImage);
            txtTen = itemView.findViewById(R.id.lineMessengerNameUser);
            txtMessenger = itemView.findViewById(R.id.lineTextMessenger);
        }
    }
}
