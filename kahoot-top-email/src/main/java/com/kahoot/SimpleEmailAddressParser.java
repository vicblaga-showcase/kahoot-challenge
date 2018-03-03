package com.kahoot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * Regex based email address parser.
 */
public class SimpleEmailAddressParser implements EmailAddressParser {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    @Override
    public EmailAddress parse(String s) {
        if (!isValid(s)) {
            throw new IllegalArgumentException(format("Invalid email address %s", s));
        }

        String[] parts = s.split("@", 2);
        return new EmailAddress(parts[0], parts[1]);
    }

    @Override
    public boolean isValid(String s) {
        Matcher matcher = EMAIL_PATTERN.matcher(s);
        return matcher.matches();
    }
}
