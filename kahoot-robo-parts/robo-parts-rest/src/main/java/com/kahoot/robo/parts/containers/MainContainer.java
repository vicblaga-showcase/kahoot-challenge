package com.kahoot.robo.parts.containers;

import com.google.gson.Gson;
import com.kahoot.robo.parts.core.CompatibilitiesRepository;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.controllers.*;
import com.typesafe.config.Config;
import joni.dep.CustomContainer;

public class MainContainer extends CustomContainer {
    public MainContainer(Config config) {
        super(config);
    }

    public GetAllPartsController getAllPartsController() {
        return new GetAllPartsController(
                get(PartsRepository.class)
        );
    }

    public GetPartController getPartController() {
        return new GetPartController(
                get(PartsRepository.class)
        );
    }

    public AddCompatibilityController addCompatibilityController() {
        return new AddCompatibilityController(
                get(PartsRepository.class),
                get(CompatibilitiesRepository.class)
        );
    }

    public RemoveCompatibilityController removeCompatibilityController() {
        return new RemoveCompatibilityController(
                get(PartsRepository.class),
                get(CompatibilitiesRepository.class)
        );
    }

    public AddPartController addPartController() {
        return new AddPartController(
                new Gson(),
                get(PartsRepository.class)
        );
    }

    public DeletePartController deletePartController() {
        return new DeletePartController(
                get(PartsRepository.class),
                get(CompatibilitiesRepository.class)
        );
    }

    public GetCompatiblePartsController getCompatiblePartsController() {
        return new GetCompatiblePartsController(
                get(PartsRepository.class),
                get(CompatibilitiesRepository.class)
        );
    }

    public UpdatePartController updatePartController() {
        return new UpdatePartController(
                new Gson(),
                get(PartsRepository.class)
        );
    }
}
