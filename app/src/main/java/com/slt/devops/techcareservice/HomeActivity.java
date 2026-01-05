package com.slt.devops.techcareservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class HomeActivity extends AppCompatActivity {

    CardView cardServices, cardBookings, cardProfile, cardSupport;
    TextView txtUserName;

    FirebaseAuth auth;
    FirebaseFirestore db;
    ListenerRegistration userListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        txtUserName = findViewById(R.id.txtUserName);
        cardServices = findViewById(R.id.cardServices);
        cardBookings = findViewById(R.id.cardBookings);
        cardProfile = findViewById(R.id.cardProfile);
        cardSupport = findViewById(R.id.cardSupport);

        listenToUserName();

        cardServices.setOnClickListener(v ->
                startActivity(new Intent(this, ServiceListActivity.class)));

        cardBookings.setOnClickListener(v ->
                startActivity(new Intent(this, BookingsActivity.class)));

        cardProfile.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        cardSupport.setOnClickListener(v ->
                startActivity(new Intent(this, SupportActivity.class)));

        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        auth.signOut();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    private void listenToUserName() {
        if (auth.getCurrentUser() == null) return;

        userListener = db.collection("users")
                .document(auth.getCurrentUser().getUid())
                .addSnapshotListener((doc, error) -> {
                    if (doc != null && doc.exists()) {
                        txtUserName.setText(doc.getString("name"));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userListener != null) userListener.remove();
    }
}
