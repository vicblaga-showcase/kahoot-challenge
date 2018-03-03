package com.kahoot.robo.parts.rest.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Value;

@Value
public class PartResponse {
    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
}
