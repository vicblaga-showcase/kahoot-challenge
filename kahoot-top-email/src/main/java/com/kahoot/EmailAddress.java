package com.kahoot;

public class EmailAddress {
    private final String name;
    private final String domain;

    public EmailAddress(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }
}
