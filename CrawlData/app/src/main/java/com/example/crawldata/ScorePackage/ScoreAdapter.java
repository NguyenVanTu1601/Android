package com.example.crawldata.ScorePackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crawldata.R;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private List<Score> scores;
    private Context context;

    public ScoreAdapter(List<Score> scores, Context context) {
        this.scores = scores;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_score,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Score score = scores.get(position);
        holder.txtNameScore.setText(score.getTen_mon());
        holder.txtSotin.setText(score.getSo_tin()+" tín");
        holder.txtDiem10.setText(score.getDiem_he_10()+"");
        holder.txtDiem4.setText(score.getDiem_he_4());

    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNameScore, txtSotin, txtDiem10, txtDiem4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameScore = itemView.findViewById(R.id.itemNameScore);
            txtSotin = itemView.findViewById(R.id.tin_score);
            txtDiem10 = itemView.findViewById(R.id.d10_score);
            txtDiem4 = itemView.findViewById(R.id.d4_score);
        }
    }
}
