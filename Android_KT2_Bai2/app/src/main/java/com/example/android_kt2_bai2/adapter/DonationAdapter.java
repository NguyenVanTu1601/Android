package com.example.android_kt2_bai2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_kt2_bai2.R;
import com.example.android_kt2_bai2.activity.EditDonateActivity;
import com.example.android_kt2_bai2.model.Donation;

import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {

    private List<Donation> donations;
    private Context context;

    public DonationAdapter(List<Donation> donations, Context context) {
        this.donations = donations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_donation,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donation donation = donations.get(position);
        holder.textId.setText("Mã : " + donation.getId());
        holder.textName.setText("Tên : " + donation.getName());
        holder.textCity.setText("Thành phố : " + donation.getCity());
        holder.textDate.setText("Thời gian : " + donation.getDate());
        holder.textMoney.setText("Tiền quyên góp : " + donation.getMoney());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, EditDonateActivity.class);
                intent.putExtra("donation", donation);
                context.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textId, textName, textCity, textDate, textMoney;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textId      = itemView.findViewById(R.id.item_id);
            textName      = itemView.findViewById(R.id.item_name);
            textCity      = itemView.findViewById(R.id.item_city);
            textDate      = itemView.findViewById(R.id.item_date);
            textMoney      = itemView.findViewById(R.id.item_money);
        }
    }

    public void setDonation(List<Donation> donations){
        this.donations = donations;
    }
}
