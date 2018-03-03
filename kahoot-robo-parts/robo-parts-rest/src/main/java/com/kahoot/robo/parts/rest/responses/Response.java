package com.kahoot.robo.parts.rest.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Response {
    @SerializedName("status") private final String status;
    private final transient int code;
}
