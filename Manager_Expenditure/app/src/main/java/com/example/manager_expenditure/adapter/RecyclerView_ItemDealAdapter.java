package com.example.manager_expenditure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manager_expenditure.R;
import com.example.manager_expenditure.deal.item;
import com.example.manager_expenditure.listioner.PublicFunction;

import java.util.List;

public class RecyclerView_ItemDealAdapter extends RecyclerView.Adapter<RecyclerView_ItemDealAdapter.ViewHolderItemDeal> {
    private List<item> listItemDeal;
    private Context context;

    public RecyclerView_ItemDealAdapter(List<item> listItemDeal, Context context) {
        this.listItemDeal = listItemDeal;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderItemDeal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemDeal(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item_dealitem,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItemDeal holder, int position) {
        item item = listItemDeal.get(position);
        holder.textNameItemDeal.setText(item.getNameItem());
        holder.textFromItemDeal.setText(item.getFromCredit());
        holder.textMoneyItemDeal.setText(new PublicFunction().FormatMoney(item.getMoneyItem()));

    }

    @Override
    public int getItemCount() {
        return listItemDeal.size();
    }

    public class ViewHolderItemDeal extends RecyclerView.ViewHolder {

        TextView textNameItemDeal, textFromItemDeal, textMoneyItemDeal;

        public ViewHolderItemDeal(@NonNull View itemView) {
            super(itemView);
            textNameItemDeal = itemView.findViewById(R.id.itemNameDeal);
            textFromItemDeal = itemView.findViewById(R.id.itemFromDeal);
            textMoneyItemDeal = itemView.findViewById(R.id.itemMoneyDeal);

        }
    }
}
