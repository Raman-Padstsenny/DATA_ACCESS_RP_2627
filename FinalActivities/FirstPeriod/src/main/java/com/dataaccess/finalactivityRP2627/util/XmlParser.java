package com.dataaccess.finalactivityRP2627.util;

import com.dataaccess.finalactivityRP2627.model.Passenger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    public List<Passenger> parsePassengers(Path filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(filePath.toFile());
        document.getDocumentElement().normalize();

        NodeList nodes = document.getElementsByTagName("passenger");
        List<Passenger> passengers = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            String passportNo = getTagValue(element, "passportno");
            String firstName = getTagValue(element, "firstname");
            String lastName = getTagValue(element, "lastname");
            String address = getTagValue(element, "address");
            String phone = getTagValue(element, "phone");

            passengers.add(new Passenger(passportNo, firstName, lastName, address, phone));
        }

        return passengers;
    }

    private String getTagValue(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);

        if (nodes.getLength() == 0) {
            return null;
        }

        String value = nodes.item(0).getTextContent();
        return value == null ? null : value.trim();
    }
}
