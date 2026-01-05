package com.slt.devops.techcareservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminDashboardAdapter extends RecyclerView.Adapter<AdminDashboardAdapter.ViewHolder> {

    private final Context context;
    private final List<AdminDashboardItem> items;

    public AdminDashboardAdapter(Context context, List<AdminDashboardItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_admin_dashboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdminDashboardItem item = items.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.imgIcon.setImageResource(item.getIconResId());

        holder.itemView.setOnClickListener(v -> {
            switch (item.getTitle()) {
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}

