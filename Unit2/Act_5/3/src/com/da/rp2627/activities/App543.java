package com.da.rp2627.activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App543 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the file name: ");
            File file = new File(scanner.nextLine());
            if (!file.isFile()) {
                System.out.println("The file does not exist or is not a regular file.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int linesShown = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    if (linesShown == 23) {
                        System.out.print("Press Enter to continue...");
                        if (!scanner.hasNextLine()) {
                            System.out.println("\nEnd of input. Display stopped.");
                            return;
                        }
                        scanner.nextLine();
                        linesShown = 0;
                    }
                    System.out.println(line);
                    linesShown++;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read the file: " + e.getMessage());
        }
    }
}
