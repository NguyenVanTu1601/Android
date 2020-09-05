package com.example.manager_expenditure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.manager_expenditure.R;
import com.example.manager_expenditure.credit.Credit;
import com.example.manager_expenditure.listioner.PublicFunction;
import com.example.manager_expenditure.overview.itemOverview;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerView_OverviewAdapter extends RecyclerView.Adapter<RecyclerView_OverviewAdapter.ViewHolderOverview>{

    private List<itemOverview> listItemOverview;
    private Context context;

    public RecyclerView_OverviewAdapter(List<itemOverview> listItemOverview, Context context) {
        this.listItemOverview = listItemOverview;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderOverview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderOverview(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item_overview,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOverview holder, int position) {
        final itemOverview item = listItemOverview.get(position);
        holder.textMoney.setText(new PublicFunction().FormatMoney(item.getMoneyItem()));
        holder.textName.setText(item.getNameItem());
        holder.numberProgressBar.setProgress(0);

        holder.layoutItemOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBehavior(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemOverview.size();
    }

    public class ViewHolderOverview extends RecyclerView.ViewHolder {

        TextView textName, textMoney;
        NumberProgressBar numberProgressBar;
        ImageView imageItemOverview;
        LinearLayout layoutItemOverview;

        public ViewHolderOverview(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textNameItemOverview);
            textMoney = itemView.findViewById(R.id.textMoneyItemOverview);
            numberProgressBar = itemView.findViewById(R.id.numberProgessbarItemOverview);
            imageItemOverview = itemView.findViewById(R.id.imgItemOverview);
            layoutItemOverview = itemView.findViewById(R.id.layout_item_overview);
        }
    }

    private void openBehavior(itemOverview itemOverview){
        View dialogView = LayoutInflater.from(context).inflate(R.layout.behavior_item_overview, null);

        FloatingActionButton imgOverview = dialogView.findViewById(R.id.behavior_floating_item_overview);
        TextView textNameBehavior = dialogView.findViewById(R.id.behavior_name_item_overview);
        TextView textSogiaodich = dialogView.findViewById(R.id.behavior_sogiaodich_item_overview);
        TextView textMoneyBehavior = dialogView.findViewById(R.id.behavior_money_item_overview);
        NumberProgressBar numberProgressBar = dialogView.findViewById(R.id.behavior_numberProgessbar_ItemOverview);
        TextView textDateBehavior = dialogView.findViewById(R.id.behavior_date_item_overview);
        TextView textSumMoneyBehavior = dialogView.findViewById(R.id.behavior_tongtien_item_overview);

        textNameBehavior.setText(itemOverview.getNameItem());
        textMoneyBehavior.setText(new PublicFunction().FormatMoney(itemOverview.getMoneyItem()));

        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(dialogView);

        dialog.show();
    }
}
