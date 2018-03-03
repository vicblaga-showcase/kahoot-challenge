package com.kahoot.robo.parts.rest.responses;

public class Error extends Response {
    private final String message;

    public Error(String message) {
        super("ERROR", 400);
        this.message = message;
    }
}
