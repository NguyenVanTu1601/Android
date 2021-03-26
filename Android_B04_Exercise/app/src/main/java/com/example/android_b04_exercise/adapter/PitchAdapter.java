package com.example.android_b04_exercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_b04_exercise.R;
import com.example.android_b04_exercise.model.Pitch;

import java.util.ArrayList;

public class PitchAdapter extends RecyclerView.Adapter<PitchAdapter.PitchViewHolder> {

    private ArrayList<Pitch> pitches;
    private Context context;

    public PitchAdapter(ArrayList<Pitch> pitches, Context context) {
        this.pitches = pitches;
        this.context = context;
    }

    @NonNull
    @Override
    public PitchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_item,parent,false);
        return new PitchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PitchViewHolder holder, int position) {
        Pitch pitch = pitches.get(position);
        holder.imageView.setImageResource(pitch.getImage());
        holder.txtName.setText(pitch.getName());
        holder.txtLocation.setText(pitch.getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Bạn chọn sân bóng : " + pitch.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pitches.size();
    }

    public class PitchViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView txtName, txtLocation;

        public PitchViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView   = itemView.findViewById(R.id.image_item);
            txtName     = itemView.findViewById(R.id.name_item);
            txtLocation = itemView.findViewById(R.id.location_item);
        }
    }
}
