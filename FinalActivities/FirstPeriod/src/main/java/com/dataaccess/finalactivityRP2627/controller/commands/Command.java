package com.dataaccess.finalactivityRP2627.controller.commands;

import com.dataaccess.finalactivityRP2627.util.AppUtils;

public interface Command {
    void execute(String[] args);

    default void showHelp(){
        System.out.println(AppUtils.getHelpText());
    }
}
