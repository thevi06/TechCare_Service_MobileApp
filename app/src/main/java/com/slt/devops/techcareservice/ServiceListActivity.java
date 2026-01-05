package com.slt.devops.techcareservice;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ServiceListActivity extends AppCompatActivity {

    CardView cardMobile, cardLaptop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        cardMobile = findViewById(R.id.cardMobile);
        cardLaptop = findViewById(R.id.cardLaptop);

        cardMobile.setOnClickListener(v -> openBooking("Mobile Repair"));
        cardLaptop.setOnClickListener(v -> openBooking("Laptop Repair"));
    }

    private void openBooking(String serviceName) {
        Intent intent = new Intent(ServiceListActivity.this, CreateBookingActivity.class);
        intent.putExtra("service", serviceName);
        startActivity(intent);
    }

}
