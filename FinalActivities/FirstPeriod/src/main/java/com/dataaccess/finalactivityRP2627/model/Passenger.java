package com.dataaccess.finalactivityRP2627.model;

public class Passenger {

    private String passportNo;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public Passenger(String passportNo, String firstName, String lastName, String address, String phone) {
        this.passportNo = passportNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}