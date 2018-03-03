package com.kahoot.robo.parts.rest.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Value;

import java.util.Collection;

@Value
public class CompatibilitiesResponse {
    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("compatible_parts") private Collection<PartResponse> compatibleParts;
}
