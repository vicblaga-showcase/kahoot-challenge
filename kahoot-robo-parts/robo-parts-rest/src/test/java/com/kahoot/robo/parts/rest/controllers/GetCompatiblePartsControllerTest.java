package com.kahoot.robo.parts.rest.controllers;

import com.kahoot.robo.parts.core.CompatibilitiesRepository;
import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.responses.CompatibilitiesResponse;
import com.kahoot.robo.parts.rest.responses.Error;
import com.kahoot.robo.parts.rest.responses.PartResponse;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.Success;
import org.junit.Test;
import spark.Request;

import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetCompatiblePartsControllerTest {
    private PartsRepository partsRepository = mock(PartsRepository.class);
    private CompatibilitiesRepository compatibilitiesRepository = mock(CompatibilitiesRepository.class);
    private Controller controller = new GetCompatiblePartsController(partsRepository, compatibilitiesRepository);

    @Test
    public void should_return_compatibility_response() {
        // setup
        Part part1 = Part.builder().id(1).name("abcd").build();
        Part part2 = Part.builder().id(2).name("efgh").build();
        Part part3 = Part.builder().id(3).name("ijkl").build();
        when(partsRepository.getById(1)).thenReturn(Optional.of(part1));
        when(partsRepository.getById(2)).thenReturn(Optional.of(part2));
        when(partsRepository.getById(3)).thenReturn(Optional.of(part3));
        when(compatibilitiesRepository.getCompatibleParts(1)).thenReturn(newArrayList(2));
        Request request = mock(Request.class);
        when(request.params(":id")).thenReturn("1");

        // when
        Response response = controller.execute(request);

        // then
        assertEquals(Success.class, response.getClass());
        Object data = ((Success) response).getData();
        assertEquals(CompatibilitiesResponse.class, data.getClass());

        CompatibilitiesResponse compatibilitiesResponse = (CompatibilitiesResponse) data;
        assertEquals(1, compatibilitiesResponse.getId());
        assertEquals("abcd", compatibilitiesResponse.getName());
        assertEquals(1, compatibilitiesResponse.getCompatibleParts().size());

        PartResponse partResponse = compatibilitiesResponse.getCompatibleParts().iterator().next();
        assertEquals(2, partResponse.getId());
        assertEquals("efgh", partResponse.getName());
    }

    @Test
    public void should_return_error_when_part_not_found() {
        // setup
        when(partsRepository.getById(1)).thenReturn(Optional.empty());
        Request request = mock(Request.class);
        when(request.params(":id")).thenReturn("1");

        // when
        Response response = controller.execute(request);

        // then
        assertEquals(Error.class, response.getClass());
    }
}