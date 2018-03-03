package com.kahoot.robo.parts.rest.controllers;

import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.responses.Error;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.PartResponse;
import com.kahoot.robo.parts.rest.responses.Success;
import lombok.RequiredArgsConstructor;
import spark.Request;

@RequiredArgsConstructor
public class GetPartController extends Controller {
    private final PartsRepository partsRepository;

    @Override
    public Response execute(Request request) {
        try {
            int id = getIntParam(request, ":id");
            return partsRepository.getById(id).map(this::toSuccess).orElse(cannotFindError("Part", id));
        } catch (NumberFormatException ex) {
            return new Error(String.format("Cannot convert [%s] to int id", request.params(":id")));
        }
    }

    private Response toSuccess(Part part) {
        PartResponse partResponse = new PartResponse(part.getId(), part.getName());

        return new Success(partResponse);
    }

}
