package com.dataaccess.finalactivityRP2627.model;

public class Airport {

    private final String iata;
    private final String name;
    private final String city;
    private final String country;

    public Airport(String iata, String name, String city, String country) {
        this.iata = iata;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public String getIata() {
        return iata;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
