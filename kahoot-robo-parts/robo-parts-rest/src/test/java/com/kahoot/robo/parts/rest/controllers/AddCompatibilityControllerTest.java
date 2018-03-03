package com.kahoot.robo.parts.rest.controllers;

import com.kahoot.robo.parts.core.CompatibilitiesRepository;
import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.responses.Error;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.Success;
import org.junit.Test;
import spark.Request;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddCompatibilityControllerTest {
    private PartsRepository partsRepository = mock(PartsRepository.class);
    private CompatibilitiesRepository compatibilitiesRepository = mock(CompatibilitiesRepository.class);

    private AddCompatibilityController controller = new AddCompatibilityController(partsRepository,
            compatibilitiesRepository);
    @Test
    public void should_add_compatibility_if_parts_present() {
        // setup
        Part part1 = Part.builder().id(1).name("name").build();
        Part part2 = Part.builder().id(2).name("name").build();

        when(partsRepository.getById(1)).thenReturn(Optional.of(part1));
        when(partsRepository.getById(2)).thenReturn(Optional.of(part2));

        Request request = mock(Request.class);
        when(request.params(":id")).thenReturn("1");
        when(request.params(":other")).thenReturn("2");

        // when
        Response response = controller.execute(request);

        // then
        assertEquals(Success.class, response.getClass());
        verify(compatibilitiesRepository).addCompatibility(1, 2);
    }

    @Test
    public void should_not_add_compatibility_if_one_part_missing() {
        // setup
        Part part1 = Part.builder().id(1).name("name").build();

        when(partsRepository.getById(1)).thenReturn(Optional.of(part1));
        when(partsRepository.getById(2)).thenReturn(Optional.empty());

        Request request = mock(Request.class);
        when(request.params(":id")).thenReturn("1");
        when(request.params(":other")).thenReturn("2");

        // when
        Response response = controller.execute(request);

        // then
        assertEquals(Error.class, response.getClass());
        verifyNoMoreInteractions(compatibilitiesRepository);
    }
}