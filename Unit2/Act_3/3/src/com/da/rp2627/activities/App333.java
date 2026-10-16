package com.da.rp2627.activities;

import java.util.Scanner;

public class App333 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            MyDate date = new MyDate();
            while (true) {
                int day = readNumber(scanner, "Enter the day: ");
                try {
                    date.setDay(day);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            while (true) {
                int month = readNumber(scanner, "Enter the month: ");
                try {
                    date.setMonth(month);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            int year = readNumber(scanner, "Enter the year: ");
            date.setYear(year);

            System.out.println("Date: " + date);
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
