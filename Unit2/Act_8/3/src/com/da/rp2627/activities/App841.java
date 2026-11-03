package com.da.rp2627.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class App841 {
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
            throws ParserConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement("contactlist");
        document.appendChild(root);

        for (Contact contact : contacts) {
            Element element = document.createElement("contact");
            root.appendChild(element);
            addElement(document, element, "name", contact.getName());
            addElement(document, element, "surname", contact.getSurname());
            addElement(document, element, "email", contact.getEmail());
            addElement(document, element, "phone", contact.getPhone());
            addElement(document, element, "description", contact.getDescription());
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        try (FileOutputStream output = new FileOutputStream(file)) {
            transformer.transform(new DOMSource(document), new StreamResult(output));
        }
    }

    private static void addElement(Document document, Element parent, String tag, String value) {
        Element element = document.createElement(tag);
        element.setTextContent(value);
        parent.appendChild(element);
    }

    private static ArrayList<Contact> readXml(File file)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder loader = factory.newDocumentBuilder();
        Document document = loader.parse(file);
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        if (!root.getTagName().equals("contactlist")) {
            throw new IOException("Expected a contactlist root element.");
        }

        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                checkText(node);
                continue;
            }
            Element element = (Element) node;
            if (!element.getTagName().equals("contact")) {
                throw new IOException("Expected a contact element.");
            }
        }

        ArrayList<Contact> contacts = new ArrayList<>();
        NodeList contactNodes = document.getElementsByTagName("contact");
        for (int i = 0; i < contactNodes.getLength(); i++) {
            Node node = contactNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element contact = (Element) node;
                contacts.add(readContact(contact));
            }
        }
        return contacts;
    }

    private static Contact readContact(Element contact) throws IOException {
        NodeList children = contact.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                checkText(node);
                continue;
            }
            Element element = (Element) node;
            String tag = element.getTagName();
            if (!tag.equals("name") && !tag.equals("surname") && !tag.equals("email")
                    && !tag.equals("phone") && !tag.equals("description")) {
                throw new IOException("Unknown contact field: " + tag);
            }
            if (element.getElementsByTagName("*").getLength() != 0) {
                throw new IOException("Contact fields must contain text only.");
            }
        }
        String name = readField(contact, "name");
        String surname = readField(contact, "surname");
        String email = readField(contact, "email");
        String phone = readField(contact, "phone");
        String description = readField(contact, "description");
        return new Contact(name, surname, email, phone, description);
    }

    private static String readField(Element contact, String tag) throws IOException {
        NodeList fields = contact.getElementsByTagName(tag);
        if (fields.getLength() != 1) {
            throw new IOException("Each contact must contain exactly one " + tag + " element.");
        }
        Node field = fields.item(0);
        return field.getTextContent();
    }

    private static void checkText(Node node) throws IOException {
        if ((node.getNodeType() == Node.TEXT_NODE || node.getNodeType() == Node.CDATA_SECTION_NODE)
                && !node.getTextContent().trim().isEmpty()) {
            throw new IOException("Unexpected text outside a contact field.");
        }
    }
}
