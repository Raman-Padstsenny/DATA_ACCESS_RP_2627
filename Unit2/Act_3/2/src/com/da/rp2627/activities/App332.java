package com.da.rp2627.activities;

import java.util.Scanner;

public class App332 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int day = readNumber(scanner, "Enter the day: ", 1, 31);
            int month = readNumber(scanner, "Enter the month: ", 1, 12);
            int year = readNumber(scanner, "Enter the year: ", Integer.MIN_VALUE, Integer.MAX_VALUE);

            System.out.println("Date: " + day + "/" + month + "/" + year);
        }
    }

    private static int readNumber(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                scanner.next();
                continue;
            }

            int value = scanner.nextInt();
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Please enter a value between " + min + " and " + max + ".");
        }
    }
}
