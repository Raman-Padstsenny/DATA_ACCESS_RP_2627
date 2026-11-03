package com.da.rp2627.activities;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class App821 {
    public static void main(String[] args) {
        File file = new File("Unit2/Act_8/1/src/com/da/rp2627/activities/contacts.xml");
        if (!file.isFile()) {
            System.out.println("The contacts XML file does not exist or is not a regular file.");
            return;
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(file, new ContactHandler());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Could not read contacts: " + e.getMessage());
        }
    }

    private static class ContactHandler extends DefaultHandler {
        private final StringBuilder text = new StringBuilder();
        private String name;
        private String surname;
        private String cell;
        private String work;
        private String home;
        private boolean inPhones;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            text.setLength(0);
            if (qName.equals("contact")) {
                name = "";
                surname = "";
                cell = "";
                work = "";
                home = "";
                inPhones = false;
            } else if (qName.equals("phones")) {
                inPhones = true;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            text.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            String value = text.toString().trim();
            switch (qName) {
                case "name":
                    name = value;
                    break;
                case "surname":
                    surname = value;
                    break;
                case "cell":
                    if (inPhones) {
                        cell = value;
                    }
                    break;
                case "work":
                    if (inPhones) {
                        work = value;
                    }
                    break;
                case "home":
                    if (inPhones) {
                        home = value;
                    }
                    break;
                case "phones":
                    inPhones = false;
                    break;
                case "contact":
                    String phone = !cell.isEmpty() ? cell : !work.isEmpty() ? work : home;
                    if (phone.isEmpty()) {
                        phone = "No phone number";
                    }
                    System.out.println((name + " " + surname).trim() + " - " + phone);
                    break;
                default:
                    break;
            }
        }
    }
}
