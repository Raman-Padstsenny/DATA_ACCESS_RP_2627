package com.da.rp2627.activities;

import java.util.Scanner;

public class App334 {
    public static void main(String[] args) {
        MyDateExt date = new MyDateExt();
        try (Scanner scanner = new Scanner(System.in)) {
            int year = readNumber(scanner, "Enter the year: ");
            date.setYear(year);

            while (true) {
                try {
                    int month = readNumber(scanner, "Enter the month: ");
                    date.setMonth(month);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            while (true) {
                try {
                    int day = readNumber(scanner, "Enter the day: ");
                    date.setDay(day);
                    System.out.println("Date: " + date);
                    if (date.isLeapYear()) {
                        System.out.println(year + " is a leap year (366 days).");
                    } else {
                        System.out.println(year + " is not a leap year (365 days).");
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please enter the date again.");
                }
            }
        }
    }

    private static int readNumber(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                scanner.next();
                continue;
            }

            return scanner.nextInt();
        }
    }
}
