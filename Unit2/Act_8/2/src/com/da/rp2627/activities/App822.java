package com.da.rp2627.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

public class App822 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("1. Convert contacts.obj to contacts.xml");
            System.out.println("2. Convert contacts.xml to contacts.obj");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();
            if (!option.equals("1") && !option.equals("2")) {
                System.out.println("Invalid option.");
                return;
            }

            boolean toXml = option.equals("1");
            File source = new File(toXml ? "contacts.obj" : "contacts.xml");
            File destination = new File(toXml ? "contacts.xml" : "contacts.obj");
            if (!source.isFile()) {
                System.out.println("The source file does not exist or is not a regular file.");
                return;
            }
            if (destination.exists()) {
                System.out.print("Overwrite " + destination.getName() + "? (y/n): ");
                if (!scanner.nextLine().equalsIgnoreCase("y")) {
                    System.out.println("Conversion cancelled.");
                    return;
                }
            }

            if (toXml) {
                writeXml(destination, readObjects(source));
            } else {
                writeObjects(destination, readXml(source));
            }
            System.out.println("Converted " + source.getName() + " to " + destination.getName() + ".");
        } catch (IOException | ClassNotFoundException | ParserConfigurationException
                 | SAXException | TransformerException e) {
            System.out.println("Could not convert contacts: " + e.getMessage());
        }
    }

    private static ArrayList<Contact> readObjects(File file) throws IOException, ClassNotFoundException {
        ArrayList<Contact> contacts = new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
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

    private static void writeObjects(File file, ArrayList<Contact> contacts) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeInt(contacts.size());
            for (Contact contact : contacts) {
                output.writeObject(contact);
            }
        }
    }

    private static void writeXml(File file, ArrayList<Contact> contacts)
            throws TransformerException, SAXException, IOException {
        SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();
        Transformer transformer = handler.getTransformer();
        try (FileOutputStream output = new FileOutputStream(file)) {
            handler.setResult(new StreamResult(output));
            AttributesImpl attributes = new AttributesImpl();
            handler.startDocument();
            handler.startElement("", "", "contactlist", attributes);
            for (Contact contact : contacts) {
                handler.startElement("", "", "contact", attributes);
                writeElement(handler, "name", contact.getName());
                writeElement(handler, "surname", contact.getSurname());
                writeElement(handler, "email", contact.getEmail());
                writeElement(handler, "phone", contact.getPhone());
                writeElement(handler, "description", contact.getDescription());
                handler.endElement("", "", "contact");
            }
            handler.endElement("", "", "contactlist");
            handler.endDocument();
        }
    }

    private static void writeElement(TransformerHandler handler, String tag, String value)
            throws SAXException {
        handler.startElement("", "", tag, new AttributesImpl());
        char[] characters = value.toCharArray();
        handler.characters(characters, 0, characters.length);
        handler.endElement("", "", tag);
    }

    private static ArrayList<Contact> readXml(File file)
            throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        ContactHandler handler = new ContactHandler();
        factory.newSAXParser().parse(file, handler);
        return handler.contacts;
    }

    private static class ContactHandler extends DefaultHandler {
        private final ArrayList<Contact> contacts = new ArrayList<>();
        private final HashMap<String, String> fields = new HashMap<>();
        private final StringBuilder text = new StringBuilder();
        private int depth;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            if (depth == 0) {
                if (!qName.equals("contactlist")) {
                    throw new SAXException("Expected a contactlist root element.");
                }
            } else if (depth == 1) {
                if (!qName.equals("contact")) {
                    throw new SAXException("Expected a contact element.");
                }
                fields.clear();
            } else if (depth == 2) {
                if (!qName.equals("name") && !qName.equals("surname") && !qName.equals("email")
                        && !qName.equals("phone") && !qName.equals("description")) {
                    throw new SAXException("Unknown contact field: " + qName);
                }
                if (fields.containsKey(qName)) {
                    throw new SAXException("Duplicate contact field: " + qName);
                }
                text.setLength(0);
            } else {
                throw new SAXException("Contact fields must contain text only.");
            }
            depth++;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (depth == 3) {
                text.append(ch, start, length);
            } else if (!new String(ch, start, length).trim().isEmpty()) {
                throw new SAXException("Unexpected text outside a contact field.");
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (depth == 3) {
                fields.put(qName, text.toString());
            } else if (depth == 2) {
                if (fields.size() != 5) {
                    throw new SAXException("Each contact must contain name, surname, email, phone and description.");
                }
                contacts.add(new Contact(fields.get("name"), fields.get("surname"),
                        fields.get("email"), fields.get("phone"), fields.get("description")));
            }
            depth--;
        }
    }
}
