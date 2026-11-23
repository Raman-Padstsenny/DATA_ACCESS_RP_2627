package com.dataaccess.finalactivityRP2627.repository;

import com.dataaccess.finalactivityRP2627.model.Airport;
import com.dataaccess.finalactivityRP2627.model.Flight;
import com.dataaccess.finalactivityRP2627.model.Passenger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Repository {


    public void savePassengers(List<Passenger> passengers) throws SQLException, IOException {
        try (Connection connection = DBConnector.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Passenger passenger : passengers) {
                    savePassenger(passenger, connection);
                }
            } catch (Exception exception) {
                connection.rollback();
                throw exception;
            }
            connection.commit();
        }
    }

    private void savePassenger(Passenger passenger, Connection connection) throws SQLException {
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

    public List<Flight> findAvailableFlights(String origin, String destination, LocalDate date)
            throws SQLException, IOException {
//        see the SQL of the function that was created in supabase DB in the resources/sql/functions_RP_2627.sql
        String sql = "SELECT flight_code, airline, origin, destination, departure, arrival, " +
                "duration, free_seats, total_seats FROM get_available_flights_RP_2627(?, ?, ?)";
        List<Flight> flights = new ArrayList<>();

        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(sql)) {
            statement.setString(1, origin);
            statement.setString(2, destination);
            statement.setString(3, Date.valueOf(date).toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    flights.add(new Flight(
                            resultSet.getString("flight_code"),
                            resultSet.getString("airline"),
                            resultSet.getString("origin"),
                            resultSet.getString("destination"),
                            resultSet.getTime("departure").toLocalTime(),
                            resultSet.getTime("arrival").toLocalTime(),
                            resultSet.getInt("duration"),
                            resultSet.getInt("free_seats"),
                            resultSet.getInt("total_seats")));
                }
            }
        }
        return flights;
    }

    public List<Airport> findOriginAirports() throws SQLException, IOException {
        String sql = "SELECT a.iata, a.name, c.name AS city, c.country "
                + "FROM airports a "
                + "JOIN cities c ON c.code = a.city "
                + "WHERE EXISTS (SELECT 1 FROM flights f WHERE f.source = a.code) "
                + "ORDER BY a.iata";
        List<Airport> airports = new ArrayList<>();

        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                airports.add(new Airport(
                        resultSet.getString("iata"),
                        resultSet.getString("name"),
                        resultSet.getString("city"),
                        resultSet.getString("country")));
            }
        }
        return airports;
    }

    public List<Airport> findDestinationAirports(String origin) throws SQLException, IOException {
        String sql = "SELECT DISTINCT d.iata, d.name, c.name AS city, c.country "
                + "FROM flights f "
                + "JOIN airports s ON s.code = f.source "
                + "JOIN airports d ON d.code = f.destination "
                + "JOIN cities c ON c.code = d.city "
                + "WHERE s.iata = ? "
                + "ORDER BY d.iata";
        List<Airport> airports = new ArrayList<>();

        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(sql)) {
            statement.setString(1, origin);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    airports.add(new Airport(
                            resultSet.getString("iata"),
                            resultSet.getString("name"),
                            resultSet.getString("city"),
                            resultSet.getString("country")));
                }
            }
        }
        return airports;
    }

    public boolean airportExists(String iata) throws SQLException, IOException {
        String sql = "SELECT 1 FROM airports WHERE iata = ?";

        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(sql)) {
            statement.setString(1, iata);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean passengerExists(String passportNo) throws SQLException, IOException {
        String sql = "SELECT 1 FROM passengers WHERE passportno = ?";

        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(sql)) {
            statement.setString(1, passportNo);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public Integer findTotalSeats(String flightCode) throws SQLException, IOException {
        String sql = "SELECT p.total_seats FROM flights f "
                + "JOIN planes p ON p.plane_id = f.plane "
                + "WHERE f.flight_code = ?";

        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(sql)) {
            statement.setString(1, flightCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt("total_seats") : null;
            }
        }
    }

    public boolean hasActiveTicketOnDate(String passportNo, LocalDate date) throws SQLException, IOException {
        String sql = "SELECT 1 FROM tickets WHERE passportno = ? AND date_of_travel = ? "
                + "AND date_of_cancellation IS NULL";

        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(sql)) {
            statement.setString(1, passportNo);
            statement.setDate(2, Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean cancelTicketOnDate(String passportNo, LocalDate date) throws SQLException, IOException {
        String sql = "UPDATE tickets set date_of_cancelation = ? WHERE passportno = ? AND date_of_travel = ? ";

        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setString(2, passportNo);
            statement.setDate(3, Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public int countActiveTickets(String flightCode, LocalDate date) throws SQLException, IOException {
        String sql = "SELECT COUNT(*) FROM tickets WHERE flight_code = ? AND date_of_travel = ? "
                + "AND date_of_cancellation IS NULL";

        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(sql)) {
            statement.setString(1, flightCode);
            statement.setDate(2, Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }

    public int insertTicket(String passportNo, String flightCode, LocalDate date) throws SQLException, IOException {
        String sql = "INSERT INTO tickets (date_of_booking, date_of_travel, passportno, flight_code, price) "
                + "VALUES (CURRENT_DATE, ?, ?, ?, 12345) RETURNING ticket_number";

        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(date));
            statement.setString(2, passportNo);
            statement.setString(3, flightCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }
}
