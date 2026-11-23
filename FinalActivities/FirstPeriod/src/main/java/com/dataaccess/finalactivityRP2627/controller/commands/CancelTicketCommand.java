package com.dataaccess.finalactivityRP2627.controller.commands;

import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.service.TicketService;
import com.dataaccess.finalactivityRP2627.util.Validator;

public class CancelTicketCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            System.out.println("Error: --cancel requires exactly two arguments: <passenger_id> <date>.");
            showHelp();
            return;
        }

        TicketService ticketService = new TicketService(new Validator(), new Repository());
        ticketService.cancelTicket(args[1], args[2]);
    }
}
