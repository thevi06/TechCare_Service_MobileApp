package com.slt.devops.techcareservice;

public class Booking {
    private String id;
    private String userId; // store UID
    private String device;
    private String issue;
    private String serviceType;
    private String status;
    private String technician;
    private String estimatedTime;

    // Default constructor required for Firestore
    public Booking() {}

    // Constructor for creating a new booking
    public Booking(String userId, String device, String issue, String serviceType,
                   String status, String technician, String estimatedTime) {
        this.userId = userId;
        this.device = device;
        this.issue = issue;
        this.serviceType = serviceType;
        this.status = status;
        this.technician = technician;
        this.estimatedTime = estimatedTime;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDevice() { return device; }
    public void setDevice(String device) { this.device = device; }

    public String getIssue() { return issue; }
    public void setIssue(String issue) { this.issue = issue; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTechnician() { return technician; }
    public void setTechnician(String technician) { this.technician = technician; }

    public String getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(String estimatedTime) { this.estimatedTime = estimatedTime; }
}
