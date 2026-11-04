package com.dataaccess.finalactivityRP2627.controller;

import com.dataaccess.finalactivityRP2627.controller.commands.AddPassengerCommand;
import com.dataaccess.finalactivityRP2627.controller.commands.Command;
import com.dataaccess.finalactivityRP2627.controller.commands.HelpCommand;
import com.dataaccess.finalactivityRP2627.repository.DBConnector;

public class CommandController {

    public CommandController() {
    }

    public void execute(String[] args) {
        if (args == null || args.length == 0 || (args[0].equals("-h") || args[0].equals("--help"))) {
            new HelpCommand().execute(args);
            return;
        }

        if (!DBConnector.isDatabaseAvailable()) {
            System.err.println("Error: Database is not available. Check database.properties.");
            return;
        }

        Command command = switch (args[0]) {
            case "-a", "--add" -> new AddPassengerCommand();
            default -> new HelpCommand();
        };

        command.execute(args);
    }
}