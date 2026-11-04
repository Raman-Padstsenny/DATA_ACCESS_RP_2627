package com.dataaccess.finalactivityRP2627.controller.commands;

import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.service.PassengerService;
import com.dataaccess.finalactivityRP2627.util.Validator;
import com.dataaccess.finalactivityRP2627.util.XmlParser;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AddPassengerCommand implements Command{
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Error: --add requires exactly one XML filename.");
            showHelp();
            return;
        }

        Repository repository = new Repository();
        XmlParser xmlParser = new XmlParser();
        Validator validator = new Validator();
        PassengerService passengerService = new PassengerService(xmlParser, validator, repository);

        Path xmlPath = Paths.get(args[1]);
        passengerService.addPassengersFromXml(xmlPath);
    }
}
