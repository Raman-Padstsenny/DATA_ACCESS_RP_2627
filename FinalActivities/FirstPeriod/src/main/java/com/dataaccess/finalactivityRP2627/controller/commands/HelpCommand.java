package com.dataaccess.finalactivityRP2627.controller.commands;

import com.dataaccess.finalactivityRP2627.util.AppUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HelpCommand implements Command{
    @Override
    public void execute(String[] args) {
        showHelp();
    }
}
