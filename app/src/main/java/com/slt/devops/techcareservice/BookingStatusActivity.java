package com.slt.devops.techcareservice;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BookingStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_status);

        TextView txtStatus = findViewById(R.id.txtStatus);
        txtStatus.setText("Status: Under Repair\nEstimated Completion: 2 Days");
    }
}
