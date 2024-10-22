package com.example.myapplication.model;

public class CustomerModel {
    // Attributes
    private String phoneNumber, note;
    private int point;
    private long timeCreated;

    // Constructors
    public CustomerModel(String phoneNumber, int point, String note) {
        this.phoneNumber = phoneNumber;
        this.point = point;
        this.note = note;
        this.timeCreated = System.currentTimeMillis();
    }

    public CustomerModel() {

    }

    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }
}
