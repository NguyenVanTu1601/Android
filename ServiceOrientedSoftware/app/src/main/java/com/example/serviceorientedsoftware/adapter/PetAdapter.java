package com.example.serviceorientedsoftware.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.serviceorientedsoftware.R;
import com.example.serviceorientedsoftware.activity.DetailPetActivity;
import com.example.serviceorientedsoftware.model.Pet;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    private List<Pet> pets;
    private Context context;

    public PetAdapter(List<Pet> pets, Context context) {
        this.pets = pets;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_pets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet pet = pets.get(position);
        holder.textNamePet.setText(pet.getName() + ", " + pet.getAge() + " years");
        Glide.with(context)
                .load(pet.getImg_url())
                .centerCrop()
                .placeholder(R.drawable.dog1)
                .into(holder.imagePet);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, DetailPetActivity.class);
                intent.putExtra("pet", pet);
                context.startActivity(intent);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (pets == null){
            return 0;
        }
        return pets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imagePet;
        TextView textNamePet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePet    = itemView.findViewById(R.id.item_pets);
            textNamePet = itemView.findViewById(R.id.item_name_pets);
        }
    }

    public void setPet(List<Pet> pets){
        this.pets = pets;
        notifyDataSetChanged();
    }
}
