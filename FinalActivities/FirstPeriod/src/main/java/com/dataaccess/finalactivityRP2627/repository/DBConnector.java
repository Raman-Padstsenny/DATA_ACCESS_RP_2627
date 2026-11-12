package com.dataaccess.finalactivityRP2627.repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
    private static final String CONFIG_FILE = "database.properties";

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        Path configPath = Path.of(CONFIG_FILE);

        if (!Files.exists(configPath)) {
            throw new IOException("Database configuration file was not found: " + CONFIG_FILE);
        }

        try (InputStream inputStream = Files.newInputStream(configPath)) {
            properties.load(inputStream);
        }

        return properties;
    }

    public static Connection getConnection() throws SQLException, IOException {
        Properties config = loadProperties();

        String url = config.getProperty("url");
        String user = config.getProperty("user");
        String password = config.getProperty("password");
        String schema = config.getProperty("schema");

        if (url == null || url.isBlank() || user == null || user.isBlank() || password == null) {
            throw new IOException("Database configuration is incomplete.");
        }

        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("user", user);
        connectionProperties.setProperty("password", password);
        if (schema != null && !schema.isBlank()) {
            connectionProperties.setProperty("currentSchema", schema);
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new IOException("Error: unable to load driver class!");
        }

        return DriverManager.getConnection(url, connectionProperties);
    }

    public static boolean isDatabaseAvailable() {
        try (Connection connection = getConnection()) {
            return connection.isValid(3);
        } catch (SQLException | IOException exception) {
            return false;
        }
    }
}



