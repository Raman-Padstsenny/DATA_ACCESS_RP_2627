package com.dataaccess.finalactivityRP2627.service;

import com.dataaccess.finalactivityRP2627.model.Passenger;
import com.dataaccess.finalactivityRP2627.repository.DBConnector;
import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.util.PassengerValidator;
import com.dataaccess.finalactivityRP2627.util.PassengerXmlParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class PassengerService {
    private static final Logger LOGGER = Logger.getLogger(PassengerService.class.getName());

    private final PassengerXmlParser xmlParser;
    private final PassengerValidator validator;
    private final Repository repository;

    public PassengerService(PassengerXmlParser xmlParser, PassengerValidator validator, Repository repository) {
        this.xmlParser = xmlParser;
        this.validator = validator;
        this.repository = repository;
    }

    public void addPassengersFromXml(Path filePath) {

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            System.out.println("Error: XML file does not exist.");
            LOGGER.warning("XML file not found: " + filePath);
            return;
        }

        if (!filePath.toString().toLowerCase().endsWith(".xml")) {
            System.out.println("Error: The input file must have an XML extension.");
            LOGGER.warning("Invalid file extension: " + filePath);
            return;
        }

        List<Passenger> passengers;

        try {
            passengers = xmlParser.parse(filePath);
        } catch (Exception exception) {
            System.out.println("Error: Could not read the XML file: " + exception.getMessage());
            LOGGER.severe("XML parsing failed: " + exception.getMessage());
            return;
        }

        if (passengers.isEmpty()) {
            System.out.println("Error: The XML file contains no passengers.");
            LOGGER.warning("XML file contains no passengers.");
            return;
        }

        boolean hasErrors = false;
        Set<String> passportNumbers = new HashSet<>();

        for (int i = 0; i < passengers.size(); i++) {
            Passenger passenger = passengers.get(i);
            List<String> errors = validator.validate(passenger);
            if (!passportNumbers.add(passenger.getPassportNo())) {
                errors.add("Duplicate passport number in XML file.");
            }

            if (!errors.isEmpty()) {
                hasErrors = true;
                for (String error : errors) {
                    System.out.println("Passenger " + (i + 1) + ": " + error);
                    LOGGER.warning("Passenger " + (i + 1) + ": " + error);
                }
            }
        }

        if (hasErrors) {
            System.out.println("Import aborted. No passengers were added.");
            LOGGER.warning("Import aborted. No passengers were added.");
            return;
        }

        try (Connection connection = DBConnector.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Passenger passenger : passengers) {
                    repository.savePassenger(passenger, connection);
                }
                connection.commit();
                System.out.println(passengers.size() + " passenger(s) added successfully.");
                LOGGER.info(passengers.size() + " passenger(s) imported successfully.");
            } catch (Exception exception) {
                connection.rollback();
                System.out.println("Import aborted. No passengers were added. Reason: " + exception.getMessage());
                LOGGER.severe("Passenger import failed. Transaction rolled back: " + exception.getMessage());
            }

        } catch (Exception exception ) {
            System.out.println("Database error: " + exception.getMessage());
            LOGGER.severe("Database connection error: " + exception.getMessage());
        }
    }
}