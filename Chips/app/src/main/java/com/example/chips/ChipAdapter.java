package com.example.chips;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

public class ChipAdapter extends RecyclerView.Adapter<ChipAdapter.ViewHolder> {
    private List<Chips> listChips;
    private Context context;

    public ChipAdapter(List<Chips> listChips, Context context) {
        this.listChips = listChips;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item_chips,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chips chips = listChips.get(position);
        holder.chip.setText(chips.getName());
        holder.chip.setTextColor(Color.parseColor(chips.getColor()));
        //holder.chip.setChipBackgroundColor(ColorStateList.valueOf(0x00FF00));
    }

    @Override
    public int getItemCount() {
        return listChips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public Chip chip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.item_chipchip);
        }
    }
}
