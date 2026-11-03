package com.da.rp2627.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class App71 {
    private static final File CONTACTS_FILE = new File("contacts.obj");

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ArrayList<Contact> contacts = loadContacts();

            while (true) {
                System.out.println("\n1. Create contact");
                System.out.println("2. Show contacts");
                System.out.println("3. Search by full name");
                System.out.println("4. Search by phone");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                if (!scanner.hasNextLine()) {
                    break;
                }
                String option = scanner.nextLine();
                switch (option) {
                    case "1":
                        Contact contact = readContact(scanner);
                        contacts.add(contact);
                        saveContacts(contacts);
                        System.out.println("Contact saved.");
                        break;
                    case "2":
                        if (contacts.isEmpty()) {
                            System.out.println("The contact list is empty.");
                        }
                        for (Contact current : contacts) {
                            System.out.println(current);
                            System.out.println();
                        }
                        break;
                    case "3":
                    case "4":
                        searchContacts(scanner, contacts, option.equals("3"));
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not open contacts.obj: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not read or write contacts.obj: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load the contact class: " + e.getMessage());
        }
    }

    private static Contact readContact(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Surname: ");
        String surname = scanner.nextLine().trim();
        System.out.print("E-mail: ");
        String email = scanner.nextLine().trim();
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        return new Contact(name, surname, email, phone, description);
    }

    private static void searchContacts(Scanner scanner, ArrayList<Contact> contacts, boolean byName) {
        System.out.print(byName ? "Enter the full name: " : "Enter the phone number: ");
        String search = scanner.nextLine().trim();
        boolean found = false;

        for (Contact contact : contacts) {
            boolean matches = byName
                    ? contact.getFullName().equalsIgnoreCase(search)
                    : contact.getPhone().equals(search);
            if (matches) {
                System.out.println(contact);
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No contacts found.");
        }
    }

    private static ArrayList<Contact> loadContacts() throws IOException, ClassNotFoundException {
        ArrayList<Contact> contacts = new ArrayList<>();
        if (!CONTACTS_FILE.exists()) {
            saveContacts(contacts);
            return contacts;
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(CONTACTS_FILE))) {
            int count = input.readInt();
            if (count < 0) {
                throw new IOException("Invalid contact count.");
            }
            for (int i = 0; i < count; i++) {
                Object value = input.readObject();
                if (!(value instanceof Contact)) {
                    throw new IOException("The file contains an invalid contact.");
                }
                contacts.add((Contact) value);
            }
        }
        return contacts;
    }

    private static void saveContacts(ArrayList<Contact> contacts) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(CONTACTS_FILE))) {
            output.writeInt(contacts.size());
            for (Contact contact : contacts) {
                output.writeObject(contact);
            }
        }
    }
}
