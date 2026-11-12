package com.dataaccess.finalactivityRP2627.controller.commands;

import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.service.FlightService;
import com.dataaccess.finalactivityRP2627.util.Validator;

public class FlightsCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length != 4) {
            System.out.println("Error: --flights requires exactly three arguments: <origin> <destination> <date>.");
            showHelp();
            return;
        }

        FlightService flightService = new FlightService(new Validator(), new Repository());
        flightService.showAvailableFlights(args[1], args[2], args[3]);
    }
}
