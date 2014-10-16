package com.epam;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzmitry_Misiuk on 10/16/2014.
 */
public class StaxParser {

    private static final QName userTagName = new QName("user");
    private static final QName usersTagName = new QName("users");
    private static final QName idAttributeName = new QName("id");
    private XMLInputFactory factory = XMLInputFactory.newInstance();

    private List<User> users;
    private User user;
    private boolean isInsideUserTag;

    public StaxParser() {
    }

    List<User> parse(InputStream is) throws XMLStreamException {
        XMLEventReader eventReader = factory.createXMLEventReader(is);
        List<User> localUsers = null;
        while (eventReader.hasNext()) {
            XMLEvent xmlEvent = eventReader.nextEvent();
            switch (xmlEvent.getEventType()) {
                case XMLStreamConstants.START_DOCUMENT:
                    init();
                    break;
                case XMLStreamConstants.START_ELEMENT:
                    processStartElement(xmlEvent.asStartElement());
                    break;
                case XMLStreamConstants.CHARACTERS:
                    processCharacters(xmlEvent.asCharacters());
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    processEndElement(xmlEvent.asEndElement());
                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    localUsers = users;
                    cleanUp();
                    break;
            }
        }
        return localUsers;
    }

    private void init() {
        users = new ArrayList<User>();
    }

    private void processStartElement(StartElement startElement) {
        if (startElement.getName().equals(userTagName)){
            String strId = startElement.getAttributeByName(idAttributeName).getValue();
            user = new User();
            int id = Integer.parseInt(strId);
            user.setId(id);
            isInsideUserTag = true;
        }
    }

    private void processCharacters(Characters characters) {
        if(isInsideUserTag){
            String userName = characters.getData();
            user.setName(userName);
        }
    }

    private void processEndElement(EndElement endElement) {
        if(endElement.getName().equals(userTagName)){
            users.add(user);
            isInsideUserTag = false;
            user = null;
        }
    }

    private void cleanUp() {
        users = null;
    }
}
