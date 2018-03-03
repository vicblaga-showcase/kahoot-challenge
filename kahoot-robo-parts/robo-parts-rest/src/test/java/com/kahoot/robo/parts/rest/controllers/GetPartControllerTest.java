package com.kahoot.robo.parts.rest.controllers;

import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.responses.Error;
import com.kahoot.robo.parts.rest.responses.PartResponse;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.Success;
import org.junit.Test;
import spark.Request;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetPartControllerTest {
    private PartsRepository partsRepository = mock(PartsRepository.class);
    private GetPartController controller = new GetPartController(partsRepository);

    @Test
    public void should_return_part_response() {
        // setup
        Part part1 = Part.builder().id(1).name("abcd").build();
        when(partsRepository.getById(1)).thenReturn(Optional.of(part1));
        Request request = mock(Request.class);
        when(request.params(":id")).thenReturn("1");

        // when
        Response response = controller.execute(request);

        // then
        assertEquals(Success.class, response.getClass());

        Object data = ((Success) response).getData();
        assertEquals(PartResponse.class, data.getClass());

        PartResponse partResponse = (PartResponse) data;
        assertEquals(1, partResponse.getId());
        assertEquals("abcd", partResponse.getName());
    }

    @Test
    public void should_return_error_if_part_not_present() {
        // setup
        when(partsRepository.getById(1)).thenReturn(Optional.empty());
        Request request = mock(Request.class);
        when(request.params(":id")).thenReturn("1");

        // when
        Response response = controller.execute(request);

        // then
        assertEquals(Error.class, response.getClass());
    }
    @Test

    public void should_return_error_if_id_wrong_format() {
        // setup
        when(partsRepository.getById(1)).thenReturn(Optional.empty());
        Request request = mock(Request.class);
        when(request.params(":id")).thenReturn("abcd");

        // when
        Response response = controller.execute(request);

        // then
        assertEquals(Error.class, response.getClass());
    }
}