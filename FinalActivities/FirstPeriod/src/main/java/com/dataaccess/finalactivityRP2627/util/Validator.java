package com.dataaccess.finalactivityRP2627.util;

import com.dataaccess.finalactivityRP2627.model.Passenger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class Validator {
    public List<String> validatePassenger(Passenger passenger) {
        List<String> errors = new ArrayList<>();

        if (isBlank(passenger.getPassportNo())) {
            errors.add("Passport number is mandatory.");
        } else if (!passenger.getPassportNo().matches("^[A-Za-z]\\d{7}$")) {
            errors.add("Passport number format is invalid.");
        }

        if (isBlank(passenger.getFirstName())) {
            errors.add("First name is mandatory.");
        }

        if (isBlank(passenger.getLastName())) {
            errors.add("Last name is mandatory.");
        }

        if (passenger.getPhone() != null && passenger.getPhone().length() != 9) {
            errors.add("Phone number must be of 9 characters.");
        }

        return errors;
    }

    public boolean isValidIata(String code) {
        return code != null && code.matches("^[A-Za-z]{3}$");
    }

    public LocalDate parseDate(String text) {
        if (isBlank(text)) {
            return null;
        }
        try {
            return LocalDate.parse(text.trim(), DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
