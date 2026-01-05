package com.slt.devops.techcareservice;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {
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
