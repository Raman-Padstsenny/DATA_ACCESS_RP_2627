package com.dataaccess.finalactivityRP2627.controller.commands;

import com.dataaccess.finalactivityRP2627.repository.Repository;
import com.dataaccess.finalactivityRP2627.service.TicketService;
import com.dataaccess.finalactivityRP2627.util.Validator;

import java.util.Objects;

public class BuyTicketCommand implements Command {
    @Override
    public void execute(String[] args) {
        if ((args.length != 4 && args.length != 5) || (args.length == 5 && !Objects.equals(args[4], "--print"))) {
            System.out.println("Error: --buy requires three arguments: <customer id> <flight id> <date> and optional --print argument.");
            showHelp();
            return;
        }

        TicketService ticketService = new TicketService(new Validator(), new Repository());
        if (args.length == 4) {
            ticketService.buyTicket(args[1], args[2], args[3], false);
        } else {
            ticketService.buyTicket(args[1], args[2], args[3], true);
        }
    }
}
