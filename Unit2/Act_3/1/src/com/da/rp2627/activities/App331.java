package com.da.rp2627.activities;

import java.util.Scanner;

public class App331 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter your surnames: ");
            String surnames = scanner.nextLine();

            System.out.println("Hello, " + name + " " + surnames + "!");
        }
    }
}
