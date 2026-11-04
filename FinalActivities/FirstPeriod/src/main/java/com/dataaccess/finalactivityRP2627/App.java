package com.dataaccess.finalactivityRP2627;

import com.dataaccess.finalactivityRP2627.controller.CommandController;
import com.dataaccess.finalactivityRP2627.util.AppUtils;

public class App {

    public static void main(String[] args) {
        CommandController controller = new CommandController();

        controller.execute(args);
    }

}