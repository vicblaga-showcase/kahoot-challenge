package com.kahoot;

/**
 * Knows how to validate and parse email addresses
 */
public interface EmailAddressParser {
    /**
     * Parse a String into an EmailAddress
     * @param s the email address as string
     * @return the EmailAddress value object
     * @throws IllegalArgumentException if the string is not a valid email address
     */
    EmailAddress parse(String s);

    /**
     * Checks if a string is a valid email address.
     * @param s the email address as string
     * @return true if is a valid email address, false otherwise
     */
    boolean isValid(String s);
}
