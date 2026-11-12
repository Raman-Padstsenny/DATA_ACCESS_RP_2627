package com.dataaccess.finalactivityRP2627.controller.commands;

import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.service.FlightService;
import com.dataaccess.finalactivityRP2627.util.Validator;

public class OriginsCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Error: --origins does not accept arguments.");
            showHelp();
            return;
        }

        FlightService flightService = new FlightService(new Validator(), new Repository());
        flightService.showOrigins();
    }
}
