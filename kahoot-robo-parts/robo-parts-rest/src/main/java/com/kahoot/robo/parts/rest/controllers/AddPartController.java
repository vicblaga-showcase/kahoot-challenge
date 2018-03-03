package com.kahoot.robo.parts.rest.controllers;

import static java.lang.String.format;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.requests.AddPartRequest;
import com.kahoot.robo.parts.rest.responses.Error;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.Success;
import lombok.RequiredArgsConstructor;
import spark.Request;

@RequiredArgsConstructor
public class AddPartController extends Controller {
    private final Gson gson;
    private final PartsRepository partsRepository;

    @Override
    public Response execute(Request request) {
        try {
            AddPartRequest addPartRequest = gson.fromJson(request.body(), AddPartRequest.class);
            Part part = partsRepository.addPart(addPartRequest.getName());
            return new Success(part);
        } catch (JsonSyntaxException e) {
            return new Error(format("Cannot parse [%s] into an add part request", request.body()));
        }
    }
}
