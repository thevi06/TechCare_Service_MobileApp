package com.slt.devops.techcareservice;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BookingsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> bookings;
    ArrayAdapter<String> adapter;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        listView = findViewById(R.id.listBookings);
        bookings = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, bookings);
        listView.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadBookings();
    }

    private void loadBookings() {
        db.collection("bookings")
                .whereEqualTo("userId", auth.getCurrentUser().getUid())
                .addSnapshotListener((value, error) -> {
                    bookings.clear();
                    if (value != null) {
                        value.forEach(doc -> {
                            bookings.add(
                                    doc.getString("serviceType") + " | " +
                                            doc.getString("status")
                            );
                        });
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
