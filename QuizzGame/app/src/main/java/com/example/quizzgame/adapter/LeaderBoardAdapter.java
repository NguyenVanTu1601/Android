package com.example.quizzgame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzgame.R;
import com.example.quizzgame.databinding.RowLeaderboardsBinding;
import com.example.quizzgame.model.User;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder> {

    private ArrayList<User> users;
    private Context context;

    public LeaderBoardAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;

    }

    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.row_leaderboards, parent, false);
        return new LeaderBoardViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {
        User user = users.get(position);

        holder.binding.nameItem.setText(user.getName());
        holder.binding.coinsItem.setText(user.getCoins() + "");
        holder.binding.xh.setText(String.format("#%d", position + 1));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder{

        RowLeaderboardsBinding binding;
        public LeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowLeaderboardsBinding.bind(itemView);
        }
    }
}
