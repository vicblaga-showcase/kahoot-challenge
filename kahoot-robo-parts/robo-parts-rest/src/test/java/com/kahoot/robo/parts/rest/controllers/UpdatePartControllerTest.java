package com.kahoot.robo.parts.rest.controllers;

import com.google.gson.Gson;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.Success;
import org.junit.Test;
import spark.Request;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpdatePartControllerTest {
    private PartsRepository partsRepository = mock(PartsRepository.class);
    private UpdatePartController controller = new UpdatePartController(new Gson(), partsRepository);

    @Test
    public void should_update_part() {
        // setup
        Request request = mock(Request.class);
        when(request.body()).thenReturn("{\"id\": 1, \"name\": \"part name\"}");

        // when
        Response response = controller.execute(request);

        // then
        assertThat(response).isInstanceOf(Success.class);
        verify(partsRepository).updatePart(1,"part name");
    }
}