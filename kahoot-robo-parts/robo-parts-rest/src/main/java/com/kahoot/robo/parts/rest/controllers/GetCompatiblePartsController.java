package com.kahoot.robo.parts.rest.controllers;

import com.kahoot.robo.parts.core.CompatibilitiesRepository;
import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.responses.CompatibilitiesResponse;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.PartResponse;
import com.kahoot.robo.parts.rest.responses.Success;
import lombok.RequiredArgsConstructor;
import spark.Request;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetCompatiblePartsController extends Controller {
    private final PartsRepository partsRepository;
    private final CompatibilitiesRepository compatiblePartsRepository;

    @Override
    public Response execute(Request request) {
        int id = getIntParam(request, ":id");
        Optional<Part> partOption = partsRepository.getById(id);
        if (partOption.isPresent()) {
            Part part = partOption.get();
            Collection<Part> compatibleParts = getCompatibleParts(part);

            CompatibilitiesResponse compatibilitiesResponse = new CompatibilitiesResponse(part.getId(),
                    part.getName(),
                    mapParts(compatibleParts)
            );
            return new Success(compatibilitiesResponse);
        } else {
            return cannotFindError("Part", id);
        }
    }

    private Collection<Part> getCompatibleParts(Part part) {
        Collection<Integer> compatiblePartIds = compatiblePartsRepository.getCompatibleParts(part.getId());

        return compatiblePartIds.stream()
                .map(partsRepository::getById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private List<PartResponse> mapParts(Collection<Part> parts) {
        return parts.stream().map(p -> new PartResponse(p.getId(), p.getName())).collect(Collectors.toList());
    }
}
