package com.da.rp2627.activities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App542 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the first sorted file name: ");
            File firstFile = new File(scanner.nextLine());
            System.out.print("Enter the second sorted file name: ");
            File secondFile = new File(scanner.nextLine());
            System.out.print("Enter the output sorted file name: ");
            File destination = new File(scanner.nextLine());

            if (!firstFile.isFile() || !secondFile.isFile()) {
                System.out.println("Both input files must exist and be regular files.");
                return;
            }
            if (destination.getCanonicalFile().equals(firstFile.getCanonicalFile())
                    || destination.getCanonicalFile().equals(secondFile.getCanonicalFile())) {
                System.out.println("An input file cannot be the output file.");
                return;
            }

            try (BufferedReader first = new BufferedReader(new FileReader(firstFile));
                 BufferedReader second = new BufferedReader(new FileReader(secondFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(destination))) {
                String firstLine = first.readLine();
                String secondLine = second.readLine();

                while (firstLine != null && secondLine != null) {
                    if (firstLine.compareToIgnoreCase(secondLine) <= 0) {
                        writer.write(firstLine);
                        writer.newLine();
                        firstLine = first.readLine();
                    } else {
                        writer.write(secondLine);
                        writer.newLine();
                        secondLine = second.readLine();
                    }
                }
                while (firstLine != null) {
                    writer.write(firstLine);
                    writer.newLine();
                    firstLine = first.readLine();
                }
                while (secondLine != null) {
                    writer.write(secondLine);
                    writer.newLine();
                    secondLine = second.readLine();
                }
            }
            System.out.println("Merged files into " + destination.getName() + " (alphabetical order, ignoring case).");
        } catch (IOException e) {
            System.out.println("Could not merge files: " + e.getMessage());
        }
    }
}
