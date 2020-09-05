package com.example.manager_expenditure.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manager_expenditure.R;
import com.example.manager_expenditure.credit.Credit;
import com.example.manager_expenditure.listioner.PublicFunction;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerView_TaikhoanAdapter extends RecyclerView.Adapter<RecyclerView_TaikhoanAdapter.ViewHolder> {

    // Adapter này dùng chung cho tất cả tài khoản, tiết kiệm, nợ, hoàn thành,...
    // list_item_credit cũng dùng chung
    private List<Credit> listCredit;
    private Context context;

    public RecyclerView_TaikhoanAdapter(List<Credit> listCredit, Context context) {
        this.listCredit = listCredit;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item_credit,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Credit credit = listCredit.get(position);

        holder.textNameCredit.setText(credit.getNameCredit());

        PublicFunction publicFunction = new PublicFunction();
        holder.textMoney.setText(publicFunction.FormatMoney(credit.getMoney()));
        if (credit.getLoại().equals("Nợ")){
            holder.textMoney.setTextColor(Color.parseColor("#F1525F"));
        }
        holder.layoutItemCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBehavior(credit);


            }
        });

    }

    @Override
    public int getItemCount() {
        return listCredit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image_item;
        TextView textNameCredit, textMoney;
        LinearLayout layoutItemCredit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_item = itemView.findViewById(R.id.imageItemCredit);
            textMoney = itemView.findViewById(R.id.textMoneyCredit);
            textNameCredit = itemView.findViewById(R.id.textNameCredit);
            layoutItemCredit = itemView.findViewById(R.id.layout_item_credit);
        }
    }

    private void openBehavior(Credit credit){
        View dialogView = LayoutInflater.from(context).inflate(R.layout.behavier_credit, null);
        ImageView imageBehaviorCredit = dialogView.findViewById(R.id.image_behavier_credit);
        TextView textNameBehaviorCredit = dialogView.findViewById(R.id.name_behavior_credit);
        TextView textMoneyBehaviorCredit = dialogView.findViewById(R.id.money_behavior_credit);

        textNameBehaviorCredit.setText(credit.getNameCredit());

        // Định dạng tiền tệ
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String sMoney = currencyVN.format(credit.getMoney());

        textMoneyBehaviorCredit.setText(sMoney);

        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(dialogView);

        dialog.show();
    }


}
