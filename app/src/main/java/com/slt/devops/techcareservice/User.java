package com.slt.devops.techcareservice;

public class User {
    private String id;           // Firestore document ID
    private String name;
    private String email;
    private String phone;
    private String profileImage;
    private String role;

    // Default constructor required for Firestore
    public User() {}

    // Constructor used in AdminUsersActivity or fetching
    public User(String id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Constructor used in RegisterActivity
    public User(String name, String email, String phone, String profileImage, String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profileImage = profileImage;
        this.role = role;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
