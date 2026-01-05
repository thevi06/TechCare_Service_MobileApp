package com.slt.devops.techcareservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateBookingActivity extends AppCompatActivity {

    EditText etDevice, etIssue;
    RadioGroup rgPickup;
    Button btnSubmit;

    FirebaseFirestore db;
    FirebaseAuth auth;

    String serviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);

        serviceType = getIntent().getStringExtra("service");

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etDevice = findViewById(R.id.etDevice);
        etIssue = findViewById(R.id.etIssue);
        rgPickup = findViewById(R.id.rgPickup);
        btnSubmit = findViewById(R.id.btnSubmitBooking);

        btnSubmit.setOnClickListener(v -> submitBooking());
    }

    private void submitBooking() {
        String device = etDevice.getText().toString().trim();
        String issue = etIssue.getText().toString().trim();

        if (device.isEmpty() || issue.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = rgPickup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Select pickup option", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selected = findViewById(selectedId);
        String pickupType = selected.getText().toString();

        String uid = auth.getCurrentUser().getUid(); // ✅ store UID

        // Create booking with UID instead of name
        Booking booking = new Booking(
                uid,        // store UID
                device,
                issue,
                serviceType,
                "Pending",
                "",
                ""
        );

        db.collection("bookings")
                .add(booking)
                .addOnSuccessListener(d -> {
                    Toast.makeText(this, "Booking submitted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, BookingsActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
