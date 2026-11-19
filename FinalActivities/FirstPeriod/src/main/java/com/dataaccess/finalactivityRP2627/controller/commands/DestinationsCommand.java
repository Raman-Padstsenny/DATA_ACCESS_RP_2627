package com.dataaccess.finalactivityRP2627.controller.commands;

import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.service.FlightService;
import com.dataaccess.finalactivityRP2627.util.Validator;

public class DestinationsCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Error: --destinations requires exactly one argument: <origin>.");
            showHelp();
            return;
        }

        FlightService flightService = new FlightService(new Validator(), new Repository());
        flightService.showDestinations(args[1]);
    }
}
