package com.kahoot.robo.parts.rest.requests;

import lombok.Value;

@Value
public class UpdatePartRequest {
    private int id;
    private String name;
}
