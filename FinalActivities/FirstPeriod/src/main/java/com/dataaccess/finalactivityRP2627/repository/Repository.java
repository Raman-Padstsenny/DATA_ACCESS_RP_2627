package com.dataaccess.finalactivityRP2627.repository;

import com.dataaccess.finalactivityRP2627.model.Passenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Repository {

    public void savePassenger(Passenger passenger, Connection connection) throws SQLException {
        String sql = "INSERT INTO passengers(passportno, firstname, lastname, address, phone) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, passenger.getPassportNo());
            statement.setString(2, passenger.getFirstName());
            statement.setString(3, passenger.getLastName());
            statement.setString(4, passenger.getAddress());
            statement.setString(5, passenger.getPhone());

            statement.executeUpdate();
        }
    }
}