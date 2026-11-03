package com.dataaccess.finalactivityRP2627;

import com.dataaccess.finalactivityRP2627.repository.DBConnector;
import com.dataaccess.finalactivityRP2627.util.AppUtils;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        AppUtils.configureLogging();

        if (args.length == 0 || (args[0].equals("-h") || args[0].equals("--help"))) {
            try {
                System.out.println(AppUtils.getHelpText());
            } catch (IOException exception) {
                System.err.println("Error: Could not load help text.");
            }

            return;
        }

        if (!DBConnector.isDatabaseAvailable()) {
            System.err.println("Error: Database is not available. Check database.properties.");
            return;
        }
    }

}