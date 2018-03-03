package com.kahoot;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleEmailAddressParserTest {
    private SimpleEmailAddressParser parser = new SimpleEmailAddressParser();

    @Test
    public void parse() {
        assertEmail("one.two@email.com", "one.two", "email.com");
        assertEmail("three+four+five@g.co", "three+four+five", "g.co");
        try {
            parser.parse("three@");
            fail("Should throw exception");
        } catch (Throwable e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    public void isValid() {
        assertTrue(parser.isValid("one.two@email.com"));
        assertTrue(parser.isValid("three+four+five@email.com"));
        assertFalse(parser.isValid("three+four+five@emailcom"));
        assertFalse(parser.isValid("three+four+five@email@com"));
        assertFalse(parser.isValid("three+four+five^emailcom"));
        assertFalse(parser.isValid("three@"));
        assertFalse(parser.isValid("three+four+five@email@com"));
        assertFalse(parser.isValid("@email.com"));
    }

    private void assertEmail(String s, String expectedName, String expectedDomain) {
        EmailAddress address = parser.parse(s);
        assertEquals(expectedName, address.getName());
        assertEquals(expectedDomain, address.getDomain());
    }
}