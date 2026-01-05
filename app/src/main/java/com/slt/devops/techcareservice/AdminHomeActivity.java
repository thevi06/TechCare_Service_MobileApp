package com.slt.devops.techcareservice;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import com.slt.devops.techcareservice.AdminBookingActivity;
import com.slt.devops.techcareservice.AdminUsersActivity;
import com.slt.devops.techcareservice.ProfileActivity;
import com.slt.devops.techcareservice.R;

public class AdminHomeActivity extends AppCompatActivity {

    CardView cardViewBookings, cardManageUsers, cardProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Find the toolbar
        Toolbar toolbar = findViewById(R.id.adminToolbar);

        // Set it as the action bar
        setSupportActionBar(toolbar);

        // Optional: set the title explicitly
        getSupportActionBar().setTitle("Admin Dashboard");

        cardViewBookings = findViewById(R.id.cardViewBookings);
        cardManageUsers = findViewById(R.id.cardManageUsers);
        cardProfile = findViewById(R.id.cardProfile);

        cardViewBookings.setOnClickListener(v ->
                startActivity(new Intent(AdminHomeActivity.this, AdminBookingActivity.class))
        );

        cardManageUsers.setOnClickListener(v ->
                startActivity(new Intent(this, AdminUsersActivity.class))
        );

        cardProfile.setOnClickListener(v ->
                startActivity(new Intent(AdminHomeActivity.this, ProfileActivity.class))
        );
    }
}
