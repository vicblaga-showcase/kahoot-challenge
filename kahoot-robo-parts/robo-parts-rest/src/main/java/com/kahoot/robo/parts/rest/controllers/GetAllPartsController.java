package com.kahoot.robo.parts.rest.controllers;

import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.PartResponse;
import com.kahoot.robo.parts.rest.responses.Success;
import lombok.AllArgsConstructor;
import spark.Request;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetAllPartsController extends Controller {
    private final PartsRepository partsRepository;

    public Response execute(Request req) {
        List<Part> parts = partsRepository.getAll();
        return toResponse(parts);
    }

    private Response toResponse(List<Part> parts) {
        List<PartResponse> partResponses = parts.stream()
                .map(p -> new PartResponse(p.getId(), p.getName()))
                .collect(Collectors.toList());

        return new Success(partResponses);
    }
}
