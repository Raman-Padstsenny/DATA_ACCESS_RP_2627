package com.dataaccess.finalactivityRP2627.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppUtils {

    private static final String HELP_FILE = "/help.txt";
    private static final Logger LOGGER = Logger.getLogger("com.dataaccess.finalactivityRP2627");

    private static boolean loggingConfigured = false;

    private AppUtils() {
    }

    public static String getHelpText() {

        try (InputStream inputStream = AppUtils.class.getResourceAsStream(HELP_FILE)) {

            if (inputStream == null) {
                throw new IOException("Help file was not found.");
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            return "Error: Could not load help text.";
        }
    }

    public static void configureLogging() {

        if (loggingConfigured) {
            return;
        }

        try {
            FileHandler fileHandler = new FileHandler("flights.log", true);

            fileHandler.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO);

            loggingConfigured = true;

        } catch (IOException exception) {
            System.err.println("Warning: Could not initialize logging: " + exception.getMessage());
        }
    }

    public static void configureLogger(Logger logger) {
    }
}