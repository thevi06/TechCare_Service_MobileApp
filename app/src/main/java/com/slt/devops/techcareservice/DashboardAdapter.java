package com.slt.devops.techcareservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    Context context;
    List<DashboardItem> items;

    public DashboardAdapter(Context context, List<DashboardItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_dashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DashboardItem item = items.get(position);
        holder.tvTitle.setText(item.title);
        holder.imgIcon.setImageResource(item.iconRes);
        holder.cardView.setCardBackgroundColor(item.backgroundColor);

        holder.cardView.setOnClickListener(v -> {
            switch (item.title) {
                case "View Bookings":
                    context.startActivity(new Intent(context, AdminBookingActivity.class));
                    break;
                case "Manage Users":
                    context.startActivity(new Intent(context, AdminUsersActivity.class));
                    break;
                case "My Profile":
                    context.startActivity(new Intent(context, ProfileActivity.class));
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgIcon;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
