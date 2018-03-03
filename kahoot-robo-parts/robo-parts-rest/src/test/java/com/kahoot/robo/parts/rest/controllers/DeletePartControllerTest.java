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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeletePartControllerTest {
    private PartsRepository partsRepository = mock(PartsRepository.class);
    private CompatibilitiesRepository compatibilitiesRepository = mock(CompatibilitiesRepository.class);

    private Controller controller = new DeletePartController(partsRepository, compatibilitiesRepository);

    @Test
    public void should_delete_part_and_all_related_compatibilities() {
        // setup
        Part part = Part.builder().id(1).name("part one").build();
        when(partsRepository.deleteById(1)).thenReturn(Optional.of(part));
        Request request = mock(Request.class);
        when(request.params(":id")).thenReturn("1");

        // when
        Response response = controller.execute(request);

        // then
        assertThat(response).isInstanceOf(Success.class);
        verify(partsRepository).deleteById(1);
        verify(compatibilitiesRepository).removeAll(1);
    }

    @Test
    public void should_return_error_if_part_not_present() {
        // setup
        when(partsRepository.deleteById(1)).thenReturn(Optional.empty());
        Request request = mock(Request.class);
        when(request.params(":id")).thenReturn("1");

        // when
        Response response = controller.execute(request);

        // then
        assertThat(response).isInstanceOf(Error.class);
        verifyZeroInteractions(compatibilitiesRepository);
    }
}