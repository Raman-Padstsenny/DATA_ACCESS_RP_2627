package com.dataaccess.finalactivityRP2627;

import com.dataaccess.finalactivityRP2627.repository.DBConnector;
import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.util.AppUtils;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        AppUtils.configureLogging();

        if (args.length == 0 || (args[0].equals("-h") || args[0].equals("--help"))) {
                System.out.println(AppUtils.getHelpText());
        }

        if (!DBConnector.isDatabaseAvailable()) {
            System.err.println("Error: Database is not available. Check database.properties.");
            return;
        }

        Repository repository = new Repository();
    }

}