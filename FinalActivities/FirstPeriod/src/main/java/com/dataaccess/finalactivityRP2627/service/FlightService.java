package com.dataaccess.finalactivityRP2627.service;

import com.dataaccess.finalactivityRP2627.model.Airport;
import com.dataaccess.finalactivityRP2627.model.Flight;
import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.util.AppUtils;
import com.dataaccess.finalactivityRP2627.util.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FlightService {
    private final Validator validator;
    private final Repository repository;
    private final Logger LOGGER = AppUtils.getLogger();

    public FlightService(Validator validator, Repository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public void showAvailableFlights(String origin, String destination, String dateText) {
        List<String> errors = new ArrayList<>();

        if (!validator.isValidIata(origin)) {
            errors.add("Origin must be a 3-letter IATA code.");
        }
        if (!validator.isValidIata(destination)) {
            errors.add("Destination must be a 3-letter IATA code.");
        }
        LocalDate date = validator.parseDate(dateText);
        if (date == null) {
            errors.add("Date must be a valid date in format YYYY-MM-DD.");
        }

        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.out.println("Error: " + error);
                LOGGER.warning("Flights search: " + error);
            }
            return;
        }

        String originCode = origin.toUpperCase();
        String destinationCode = destination.toUpperCase();

        try {
            List<Flight> flights = repository.findAvailableFlights(originCode, destinationCode, date);

            if (flights.isEmpty()) {
                System.out.println("No available flights from " + originCode + " to " + destinationCode + " on " + date + ".");
                LOGGER.info("No flights found " + originCode + " -> " + destinationCode + " on " + date);
                return;
            }

            System.out.println("Available flights from " + originCode + " to " + destinationCode + " on " + date + ":");
            System.out.printf("%-10s %-20s %-9s %-9s %-10s %-10s%n", "Flight", "Airline", "Departure", "Arrival", "Duration", "Free seats");
            for (Flight flight : flights) {
                System.out.printf("%-10s %-20s %-9s %-9s %-10s %-10d%n",
                        flight.getFlightCode(),
                        flight.getAirline(),
                        flight.getDeparture(),
                        flight.getArrival(),
                        flight.getDuration() + " min",
                        flight.getFreeSeats());
            }
            LOGGER.info(flights.size() + " flight(s) found " + originCode + " -> " + destinationCode + " on " + date);
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            LOGGER.severe("Flights search failed: " + exception.getMessage());
        }
    }

    public void showOrigins() {
        try {
            List<Airport> airports = repository.findOriginAirports();

            if (airports.isEmpty()) {
                System.out.println("There are no airports with departing flights.");
                LOGGER.info("No origin airports found.");
                return;
            }

            System.out.println("Airports with departing flights:");
            System.out.printf("%-5s %-45s %-15s %-15s%n", "IATA", "Airport", "City", "Country");
            for (Airport airport : airports) {
                System.out.printf("%-5s %-45s %-15s %-15s%n",
                        airport.getIata(), airport.getName(), airport.getCity(), airport.getCountry());
            }
            LOGGER.info(airports.size() + " origin airport(s) listed.");
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            LOGGER.severe("Origins listing failed: " + exception.getMessage());
        }
    }
}
