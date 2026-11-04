package com.dataaccess.finalactivityRP2627.model;

public class Passenger {

    private final String passportNo;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phone;

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