package com.dataaccess.finalactivityRP2627.service;

import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.util.AppUtils;
import com.dataaccess.finalactivityRP2627.util.Validator;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TicketService {
    private final Validator validator;
    private final Repository repository;
    private final Logger LOGGER = AppUtils.getLogger();

    public TicketService(Validator validator, Repository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public void printTicket(int number, String passport, String flight, LocalDate date) {
        File file = new File("Ticket" + number);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("Ticket number " + number + "Passenger id " + passport + "Flight id " + flight + "Date " + date.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void buyTicket(String passportNo, String flightCode, String dateText, boolean print) {
        List<String> errors = new ArrayList<>();

        if (!validator.isValidPassportNo(passportNo)) {
            errors.add("Passenger id must be a passport number: one letter followed by 7 digits.");
        }
        if (!validator.isValidFlightCode(flightCode)) {
            errors.add("Flight id must be an alphanumeric flight code (e.g. AI2014).");
        }
        LocalDate date = validator.parseDate(dateText);
        if (date == null) {
            errors.add("Date must be a valid date in format YYYY-MM-DD.");
        }

        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.out.println("Error: " + error);
                LOGGER.warning("Ticket purchase: " + error);
            }
            return;
        }

        String passport = passportNo.toUpperCase();
        String flight = flightCode.toUpperCase();

        try {
            if (!repository.passengerExists(passport)) {
                reject("passenger " + passport + " does not exist.");
                return;
            }

            Integer totalSeats = repository.findTotalSeats(flight);
            if (totalSeats == null) {
                reject("flight " + flight + " does not exist.");
                return;
            }

            if (repository.hasActiveTicketOnDate(passport, date)) {
                reject("passenger " + passport + " already has a ticket on " + date + ".");
                return;
            }

            if (repository.countActiveTickets(flight, date) >= totalSeats) {
                reject("flight " + flight + " is full on " + date + ".");
                return;
            }

            int ticketNumber = repository.insertTicket(passport, flight, date);
            if (print) {
               printTicket(ticketNumber, passport, flight, date);
            }
            System.out.println("Ticket " + ticketNumber + " purchased for passenger " + passport
                    + " on flight " + flight + " (" + date + ").");
            LOGGER.info("Ticket " + ticketNumber + " purchased: " + passport + ", " + flight + ", " + date);
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            LOGGER.severe("Ticket purchase failed: " + exception.getMessage());
        }
    }

    private void reject(String reason) {
        System.out.println("Purchase rejected: " + reason);
        LOGGER.warning("Ticket purchase rejected: " + reason);
    }

    public void cancelTicket(String passportNo, String dateText) {
        List<String> errors = new ArrayList<>();

        if (!validator.isValidPassportNo(passportNo)) {
            errors.add("Passenger id must be a passport number: one letter followed by 7 digits.");
        }

        LocalDate date = validator.parseDate(dateText);
        if (date == null || date.isBefore(LocalDate.now())) {
            errors.add("Date must be a valid date in format YYYY-MM-DD and cannot be in the past.");
        }

        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.out.println("Error: " + error);
                LOGGER.warning("Ticket purchase: " + error);
            }
            return;
        }
        String passport = passportNo.toUpperCase();

        try {
            if (!repository.passengerExists(passport)) {
                reject("passenger " + passport + " does not exist.");
                return;
            }

            if (!repository.hasActiveTicketOnDate(passport, date)) {
                reject("passenger " + passport + " doesn´t have a ticket on " + date + ". Nothing to cancel");
                return;
            }

            if (repository.cancelTicketOnDate(passportNo, date)) {
                System.out.println("Ticket  purchased for passenger " + passport
                        + " on date " + date + " is cancelled.");
                LOGGER.info("Ticket  purchased for passenger " + passport
                        + " on date " + date + " is cancelled.");
            } else {
                System.out.println("Ticket  purchased for passenger " + passport
                        + " on date " + date + " can´t be cancelled.");
                LOGGER.info("Ticket  purchased for passenger " + passport
                        + " on date " + date + " can´t be cancelled.");            }
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            LOGGER.severe("Ticket canceling failed: " + exception.getMessage());
        }
    }
}
