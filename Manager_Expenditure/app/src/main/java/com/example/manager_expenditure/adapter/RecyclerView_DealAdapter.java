package com.example.manager_expenditure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manager_expenditure.R;
import com.example.manager_expenditure.deal.Deal;
import com.example.manager_expenditure.deal.item;
import com.example.manager_expenditure.listioner.PublicFunction;

import java.util.List;

public class RecyclerView_DealAdapter extends RecyclerView.Adapter<RecyclerView_DealAdapter.ViewHolderDeal>{

    private List<Deal> listDeal;
    private Context context;

    public RecyclerView_DealAdapter(List<Deal> listDeal, Context context) {
        this.listDeal = listDeal;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDeal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderDeal(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item_deal,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDeal holder, int position) {
        Deal deal = listDeal.get(position);
        List<item> listItemDeal = listDeal.get(position).getListItem();

        holder.textDay.setText(deal.getDay()+"");
        holder.textMoneyDeal.setText(new PublicFunction().FormatMoney(deal.getMoneyDeal()));
        holder.textDayOfWeek.setText(deal.getDayOfWeek() + "");
        holder.textMonthOfYear.setText(deal.getMonthYear() + "");

        holder.recyclerViewItemDeal.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView_ItemDealAdapter adapter = new RecyclerView_ItemDealAdapter(listItemDeal,context);
        holder.recyclerViewItemDeal.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return listDeal.size();
    }

    public class ViewHolderDeal extends RecyclerView.ViewHolder {
        TextView textDay, textDayOfWeek, textMoneyDeal, textMonthOfYear;
        RecyclerView recyclerViewItemDeal;

        public ViewHolderDeal(@NonNull View itemView) {
            super(itemView);
            textDay = itemView.findViewById(R.id.dayDeal);
            textDayOfWeek = itemView.findViewById(R.id.dayOfWeekDeal);
            textMoneyDeal = itemView.findViewById(R.id.moneyDeal);
            textMonthOfYear = itemView.findViewById(R.id.monthOfYearDeal);
            recyclerViewItemDeal = itemView.findViewById(R.id.recyclerView_item_deal);
        }
    }
}
