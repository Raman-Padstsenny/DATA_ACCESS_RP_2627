package com.dataaccess.finalactivityRP2627.controller;

import com.dataaccess.finalactivityRP2627.service.PassengerService;
import com.dataaccess.finalactivityRP2627.util.AppUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandController {

    private final PassengerService passengerService;

    public CommandController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println(AppUtils.getHelpText());
            return;
        }

        String command = args[0];

        switch (command) {
            case "-h", "--help" -> System.out.println(AppUtils.getHelpText());
            case "-a", "--add" -> {
                if (args.length != 2) {
                    System.out.println("Error: --add requires exactly one XML filename.");
                    System.out.println(AppUtils.getHelpText());
                    return;
                }

                Path xmlPath = Paths.get(args[1]);
                passengerService.addPassengersFromXml(xmlPath);
            }
            default -> {
                System.out.println(AppUtils.getHelpText());
            }
        }
    }
}