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

@RequiredArgsConstructor
public class AddCompatibilityController extends Controller {
    private final PartsRepository partsRepository;
    private final CompatibilitiesRepository compatibilitiesRepository;

    @Override
    public Response execute(Request request) {
        int id1 = getIntParam(request, ":id");
        int id2 = getIntParam(request, ":other");

        Optional<Part> part1 = partsRepository.getById(id1);
        Optional<Part> part2 = partsRepository.getById(id2);

        if (part1.isPresent() && part2.isPresent()) {
            compatibilitiesRepository.addCompatibility(part1.get().getId(), part2.get().getId());
            return new Success("OK");
        } else {
            return new Error("Parts do not exist");
        }
    }
}
