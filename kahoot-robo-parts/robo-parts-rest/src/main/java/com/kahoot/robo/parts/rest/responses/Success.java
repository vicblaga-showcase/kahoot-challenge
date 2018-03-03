package com.kahoot.robo.parts.rest.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Value;

@Value
public class Success extends Response {
    @SerializedName("data") protected final Object data;

    public Success(Object data) {
        super("SUCCESS", 200);
        this.data = data;
    }
}
