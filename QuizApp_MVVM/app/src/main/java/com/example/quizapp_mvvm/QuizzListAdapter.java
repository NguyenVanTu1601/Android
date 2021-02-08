package com.example.quizapp_mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class QuizzListAdapter extends RecyclerView.Adapter<QuizzListAdapter.QuizViewHolder> {
    private List<QuizListModel> quizListModels;
    private OnQuizListItemClick onQuizListItemClick;

    public QuizzListAdapter(OnQuizListItemClick onQuizListItemClick) {
        this.onQuizListItemClick = onQuizListItemClick;
    }

    public void setQuizListModels(List<QuizListModel> quizListModels) {
        this.quizListModels = quizListModels;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        holder.listTitle.setText(quizListModels.get(position).getName());
        String imageUrl = quizListModels.get(position).getImage();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(holder.listImage);

        String listDescription = quizListModels.get(position).getDesc();
        if (listDescription.length() > 150){
            listDescription = listDescription.substring(0,150);
        }
        holder.listDesc.setText(listDescription + "...");
        holder.listLevel.setText(quizListModels.get(position).getLevel());

    }

    @Override
    public int getItemCount() {
        if(quizListModels == null){
            return 0;
        }else{
            return quizListModels.size();
        }

    }

    public class QuizViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView listImage;
        private TextView listTitle;
        private TextView listDesc;
        private TextView listLevel;
        private Button listBtn;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);

            listImage = itemView.findViewById(R.id.item_imageview);
            listTitle = itemView.findViewById(R.id.item_quizz_title);
            listDesc = itemView.findViewById(R.id.item_quizz_description);
            listLevel = itemView.findViewById(R.id.item_quizz_difficuly);
            listBtn = itemView.findViewById(R.id.item_button);

            listBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onQuizListItemClick.onItemClick(getAdapterPosition());
        }
    }

    public interface OnQuizListItemClick{
        void onItemClick(int position);
    }
}
