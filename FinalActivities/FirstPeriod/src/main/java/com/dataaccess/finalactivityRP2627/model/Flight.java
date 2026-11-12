package com.dataaccess.finalactivityRP2627.model;

import java.time.LocalTime;

public class Flight {

    private final String flightCode;
    private final String airline;
    private final String origin;
    private final String destination;
    private final LocalTime departure;
    private final LocalTime arrival;
    private final int duration;
    private final int freeSeats;

    public Flight(String flightCode,
                  String airline,
                  String origin,
                  String destination,
                  LocalTime departure,
                  LocalTime arrival,
                  int duration,
                  int freeSeats) {
        this.flightCode = flightCode;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.duration = duration;
        this.freeSeats = freeSeats;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getAirline() {
        return airline;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public LocalTime getArrival() {
        return arrival;
    }

    public int getDuration() {
        return duration;
    }

    public int getFreeSeats() {
        return freeSeats;
    }
}
