package com.epam;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class StaxParserText {

    private static List<User> users;
    private static String fileName = "users.xml";

    @BeforeClass
    public static void parseFile() throws XMLStreamException {
        InputStream is = StaxParser.class.getClassLoader().getResourceAsStream(fileName);
        users = new StaxParser().parse(is);
    }

    @Test
    public void testCount() {
        Assert.assertEquals(3, users.size());
    }

    @Test
    public void testIncludingDzmitry(){
        User dzmitryUser = new User();
        dzmitryUser.setId(1);
        dzmitryUser.setName("Dzmitry");
        users.contains(dzmitryUser);
    }

}
