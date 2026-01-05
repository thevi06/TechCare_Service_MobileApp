package com.slt.devops.techcareservice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    ImageView imgProfile;
    EditText etName, etPhone;
    TextView txtEmail;
    Button btnUpdateProfile, btnChangePassword, btnLogout, btnChangeImage;

    FirebaseAuth auth;
    FirebaseFirestore db;

    String userRole = "user"; // default
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        imgProfile = findViewById(R.id.imgProfile);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        txtEmail = findViewById(R.id.txtEmail);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnLogout = findViewById(R.id.btnLogout);
        btnChangeImage = findViewById(R.id.btnChangeImage);

        detectRoleAndLoadProfile();

        btnChangeImage.setOnClickListener(v -> pickImage());
        btnUpdateProfile.setOnClickListener(v -> updateProfile());
        btnChangePassword.setOnClickListener(v -> showChangePasswordDialog());

        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    /* ---------------- ROLE + LOAD PROFILE ---------------- */
    private void detectRoleAndLoadProfile() {
        String uid = auth.getCurrentUser().getUid();

        db.collection("users").document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        userRole = doc.getString("role");
                        loadProfile();
                    }
                });
    }

    private void loadProfile() {
        String uid = auth.getCurrentUser().getUid();

        db.collection("users").document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        etName.setText(doc.getString("name"));
                        etPhone.setText(doc.getString("phone"));
                        txtEmail.setText(doc.getString("email"));

                        String img = doc.getString("imageUri");
                        if (img != null && !img.isEmpty()) {
                            imgProfile.setImageURI(Uri.parse(img));
                        }
                    }
                });
    }

    /* ---------------- UPDATE PROFILE ---------------- */
    private void updateProfile() {
        String uid = auth.getCurrentUser().getUid();
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users").document(uid)
                .update(
                        "name", name,
                        "phone", phone
                )
                .addOnSuccessListener(unused ->
                        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show());
    }

    /* ---------------- CHANGE PASSWORD ---------------- */
    private void showChangePasswordDialog() {
        View view = LayoutInflater.from(this)
                .inflate(R.layout.dialog_change_password, null);

        EditText etCurrent = view.findViewById(R.id.etCurrentPassword);
        EditText etNew = view.findViewById(R.id.etNewPassword);
        EditText etConfirm = view.findViewById(R.id.etConfirmPassword);
        TextView txtStrength = view.findViewById(R.id.txtPasswordStrength);

        etNew.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtStrength.setText(getPasswordStrength(s.toString()));
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        new AlertDialog.Builder(this)
                .setTitle("Change Password")
                .setView(view)
                .setPositiveButton("Update", (dialog, which) -> {
                    String current = etCurrent.getText().toString();
                    String newPass = etNew.getText().toString();
                    String confirm = etConfirm.getText().toString();

                    if (!newPass.equals(confirm)) {
                        Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    auth.getCurrentUser().reauthenticate(
                                    EmailAuthProvider.getCredential(
                                            auth.getCurrentUser().getEmail(), current))
                            .addOnSuccessListener(unused ->
                                    auth.getCurrentUser().updatePassword(newPass)
                                            .addOnSuccessListener(u ->
                                                    Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show()))
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Wrong current password", Toast.LENGTH_SHORT).show());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private String getPasswordStrength(String password) {
        if (password.length() < 6) return "Weak";
        if (password.matches(".*[A-Z].*") && password.matches(".*[0-9].*"))
            return "Strong";
        return "Medium";
    }

    /* ---------------- IMAGE PICKER ---------------- */
    ActivityResultLauncher<String> imagePicker =
            registerForActivityResult(new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            imageUri = uri;
                            imgProfile.setImageURI(uri);
                        }
                    });

    private void pickImage() {
        imagePicker.launch("image/*");
    }
}
