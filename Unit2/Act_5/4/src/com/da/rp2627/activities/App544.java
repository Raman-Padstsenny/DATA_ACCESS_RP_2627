package com.da.rp2627.activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App544 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the file name: ");
            File file = new File(scanner.nextLine());
            if (!file.isFile()) {
                System.out.println("The file does not exist or is not a regular file.");
                return;
            }
            System.out.print("Enter the string to search for (case-sensitive): ");
            String search = scanner.nextLine();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int lineNumber = 0;
                boolean found = false;
                String line;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    if (line.contains(search)) {
                        System.out.println(lineNumber + ": " + line);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No matching lines found.");
                }
            }
        } catch (IOException e) {
            System.out.println("Could not search the file: " + e.getMessage());
        }
    }
}
