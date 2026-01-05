package com.slt.devops.techcareservice;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private final Context context;
    private final List<Booking> bookings;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public BookingAdapter(Context context, List<Booking> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookings.get(position);

        holder.txtDevice.setText("Device: " + booking.getDevice());
        holder.txtIssue.setText("Issue: " + booking.getIssue());
        holder.txtService.setText("Service: " + booking.getServiceType());
        holder.txtStatus.setText("Status: " + booking.getStatus());

        // Fetch user name using UID
        db.collection("users").document(booking.getUserId())
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String userName = doc.getString("name");
                        holder.txtUser.setText(userName != null ? userName : "Unknown User");
                    } else {
                        holder.txtUser.setText("Unknown User");
                    }
                })
                .addOnFailureListener(e -> holder.txtUser.setText("Unknown User"));

        holder.btnUpdate.setOnClickListener(v -> showUpdateDialog(booking));
    }

    private void showUpdateDialog(Booking booking) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_booking, null);
        AlertDialog dialog = new AlertDialog.Builder(context).setView(dialogView).create();

        EditText etStatus = dialogView.findViewById(R.id.etStatus);
        EditText etTech = dialogView.findViewById(R.id.etTechnician);
        EditText etTime = dialogView.findViewById(R.id.etEstimatedTime);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        etStatus.setText(booking.getStatus());
        etTech.setText(booking.getTechnician());
        etTime.setText(booking.getEstimatedTime());

        btnSave.setOnClickListener(v -> {
            String status = etStatus.getText().toString().trim();
            String tech = etTech.getText().toString().trim();
            String time = etTime.getText().toString().trim();

            db.collection("bookings").document(booking.getId())
                    .update("status", status, "technician", tech, "estimatedTime", time)
                    .addOnSuccessListener(unused -> {
                        booking.setStatus(status);
                        booking.setTechnician(tech);
                        booking.setEstimatedTime(time);
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Booking updated", Toast.LENGTH_SHORT).show();
                    });
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView txtUser, txtDevice, txtIssue, txtService, txtStatus;
        Button btnUpdate;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUser = itemView.findViewById(R.id.txtUserName);
            txtDevice = itemView.findViewById(R.id.txtDevice);
            txtIssue = itemView.findViewById(R.id.txtIssue);
            txtService = itemView.findViewById(R.id.txtService);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}
