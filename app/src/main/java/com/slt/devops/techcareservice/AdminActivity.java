package com.slt.devops.techcareservice;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    RecyclerView recyclerBookings;
    BookingAdapter adapter;
    List<Booking> bookingList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = FirebaseFirestore.getInstance();
        recyclerBookings = findViewById(R.id.recyclerBookings);
        bookingList = new ArrayList<>();
        adapter = new BookingAdapter(this, bookingList);

        recyclerBookings.setLayoutManager(new LinearLayoutManager(this));
        recyclerBookings.setAdapter(adapter);

        loadBookings();
    }

    private void loadBookings() {
        db.collection("bookings")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    bookingList.clear();
                    if (value != null) {
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            Booking b = doc.toObject(Booking.class);
                            if (b != null) {
                                b.setId(doc.getId());
                                bookingList.add(b);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
