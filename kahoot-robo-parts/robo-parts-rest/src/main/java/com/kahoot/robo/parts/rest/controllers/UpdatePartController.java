package com.kahoot.robo.parts.rest.controllers;

import static java.lang.String.format;

import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.requests.UpdatePartRequest;
import com.kahoot.robo.parts.rest.responses.Error;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.Success;
import lombok.RequiredArgsConstructor;
import spark.Request;

@RequiredArgsConstructor
public class UpdatePartController extends Controller {
    private final Gson gson;
    private final PartsRepository partsRepository;

    @Override
    public Response execute(Request request) {
        try {
            String body = request.body();
            UpdatePartRequest updatePartRequest = gson.fromJson(request.body(), UpdatePartRequest.class);
            Optional<Part> part = partsRepository.updatePart(updatePartRequest.getId(),
                    updatePartRequest.getName());
            return new Success(part);
        } catch (JsonSyntaxException e) {
            return new Error(format("Cannot parse [%s] into an update part request", request.body()));
        }
    }
}
