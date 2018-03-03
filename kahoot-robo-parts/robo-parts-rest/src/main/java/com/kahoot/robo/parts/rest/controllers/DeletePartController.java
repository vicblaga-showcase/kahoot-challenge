package com.kahoot.robo.parts.rest.controllers;

import com.kahoot.robo.parts.core.CompatibilitiesRepository;
import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.responses.Error;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.Success;
import lombok.RequiredArgsConstructor;
import spark.Request;

import java.util.Optional;

import static java.lang.String.format;

@RequiredArgsConstructor
public class DeletePartController extends Controller {
    private final PartsRepository partsRepository;
    private final CompatibilitiesRepository compatibilitiesRepository;

    @Override
    public Response execute(Request request) {
        try {
            int id = getIntParam(request, ":id");
            Optional<Part> part = partsRepository.deleteById(id);
            if (part.isPresent()) {
                compatibilitiesRepository.removeAll(id);
                return toSuccess(part.get());
            } else {
                return cannotFindError("Part", id);
            }
        } catch (NumberFormatException ex) {
            return new Error(format("Cannot convert [%s] to int id", request.params(":id")));
        }
    }

    private Response toSuccess(Part part) {
        return new Success(part.getId());
    }
}
