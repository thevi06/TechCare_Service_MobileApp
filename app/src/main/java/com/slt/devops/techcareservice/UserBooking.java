package com.slt.devops.techcareservice;

public class UserBooking {
    private String userId;
    private String serviceType;
    private String device;
    private String issue;
    private String pickupType;
    private String status;

    public UserBooking() {}

    public UserBooking(String userId, String serviceType, String device, String issue, String pickupType) {
        this.userId = userId;
        this.serviceType = serviceType;
        this.device = device;
        this.issue = issue;
        this.pickupType = pickupType;
        this.status = "Pending";
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public String getServiceType() { return serviceType; }
    public String getDevice() { return device; }
    public String getIssue() { return issue; }
    public String getPickupType() { return pickupType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

