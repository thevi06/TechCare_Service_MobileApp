package com.slt.devops.techcareservice;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final Context context;
    private final List<User> users;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserAdapter(Context context, List<User> users, FirebaseFirestore db) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        holder.txtName.setText(user.getName());
        holder.txtEmail.setText(user.getEmail());
        holder.txtPhone.setText(user.getPhone());

        // Delete button functionality
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete User")
                    .setMessage("Are you sure you want to delete " + user.getName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> deleteUser(user, position))
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void deleteUser(User user, int position) {
        db.collection("users").document(user.getId())
                .delete()
                .addOnSuccessListener(unused -> {
                    users.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Failed to delete: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail, txtPhone;
        Button btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtUserName);
            txtEmail = itemView.findViewById(R.id.txtUserEmail);
            txtPhone = itemView.findViewById(R.id.txtUserPhone);
            btnDelete = itemView.findViewById(R.id.btnDeleteUser);
        }
    }
}
