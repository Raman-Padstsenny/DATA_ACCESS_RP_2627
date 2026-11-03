package com.da.rp2627.activities;

import java.io.Serializable;

public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String surname;
    private final String email;
    private final String phone;
    private final String description;

    public Contact(String name, String surname, String email, String phone, String description) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    @Override
    public String toString() {
        return "Name: " + getFullName()
                + "\nE-mail: " + email
                + "\nPhone: " + phone
                + "\nDescription: " + description;
    }
}
