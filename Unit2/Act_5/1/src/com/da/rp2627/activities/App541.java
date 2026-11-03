package com.da.rp2627.activities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App541 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the file name: ");
            File file = new File(scanner.nextLine());
            boolean append = false;
            int lineNumber = 1;
            boolean needsNewLine = false;

            if (file.exists()) {
                String choice;
                do {
                    System.out.print("Overwrite or append? (o/a): ");
                    choice = scanner.nextLine();
                } while (!choice.equalsIgnoreCase("o") && !choice.equalsIgnoreCase("a"));
                append = choice.equalsIgnoreCase("a");
            }

            if (append) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    int previous = -1;
                    int character;
                    while ((character = reader.read()) != -1) {
                        if (character == '\r' || (character == '\n' && previous != '\r')) {
                            lineNumber++;
                        }
                        previous = character;
                    }
                    needsNewLine = previous != -1 && previous != '\n' && previous != '\r';
                    if (needsNewLine) {
                        lineNumber++;
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
                System.out.println("Enter sentences. Type /exit on its own line to finish.");
                while (scanner.hasNextLine()) {
                    String sentence = scanner.nextLine();
                    if (sentence.equals("/exit")) {
                        break;
                    }
                    if (needsNewLine) {
                        writer.newLine();
                        needsNewLine = false;
                    }
                    writer.write(lineNumber + ". " + sentence);
                    writer.newLine();
                    lineNumber++;
                }
            }
            System.out.println("Notes saved.");
        } catch (IOException e) {
            System.out.println("Could not save notes: " + e.getMessage());
        }
    }
}
