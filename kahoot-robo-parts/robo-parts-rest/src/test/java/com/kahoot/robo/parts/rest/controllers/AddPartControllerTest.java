package com.kahoot.robo.parts.rest.controllers;

import com.google.gson.Gson;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.responses.Error;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.Success;
import org.junit.Test;
import spark.Request;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddPartControllerTest {
    private PartsRepository partsRepository = mock(PartsRepository.class);
    private AddPartController addPartController = new AddPartController(new Gson(), partsRepository);

    @Test
    public void should_add_part_if_request_properly_formatted() {
        // setup
        Request request = mock(Request.class);
        when(request.body()).thenReturn("{\"name\": \"part name\"}");

        // when
        Response response = addPartController.execute(request);

        // then
        assertThat(response).isInstanceOf(Success.class);
        verify(partsRepository).addPart("part name");
    }

    @Test
    public void should_return_error_if_request_badly_formatted() {
        // setup
        Request request = mock(Request.class);
        when(request.body()).thenReturn("{\"name part name\"}");

        // when
        Response response = addPartController.execute(request);

        // then
        assertThat(response).isInstanceOf(Error.class);
    }
}