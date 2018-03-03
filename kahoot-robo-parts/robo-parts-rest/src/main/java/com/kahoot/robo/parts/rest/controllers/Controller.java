package com.kahoot.robo.parts.rest.controllers;

import com.kahoot.robo.parts.rest.responses.Error;
import com.kahoot.robo.parts.rest.responses.Response;
import spark.Request;

public abstract class Controller {
    public abstract Response execute(Request request);

    protected int getIntParam(Request request, String paramName) {
        return Integer.parseInt(request.params(paramName));
    }

    protected Response cannotFindError(String name, int id) {
        String message = String.format("Cannot find [%s] with id: [%d]", name, id);
        return new Error(message);
    }
}
