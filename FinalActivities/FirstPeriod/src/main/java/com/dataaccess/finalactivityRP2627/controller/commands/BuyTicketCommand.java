package com.dataaccess.finalactivityRP2627.controller.commands;

import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.service.TicketService;
import com.dataaccess.finalactivityRP2627.util.Validator;

public class BuyTicketCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length != 4) {
            System.out.println("Error: --buy requires exactly three arguments: <customer id> <flight id> <date>.");
            showHelp();
            return;
        }

        TicketService ticketService = new TicketService(new Validator(), new Repository());
        ticketService.buyTicket(args[1], args[2], args[3]);
    }
}
