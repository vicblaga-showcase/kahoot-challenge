package com.kahoot.robo.parts.rest.controllers;

import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.rest.responses.PartResponse;
import com.kahoot.robo.parts.rest.responses.Response;
import com.kahoot.robo.parts.rest.responses.Success;
import org.junit.Test;
import spark.Request;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetAllPartsControllerTest {
    private PartsRepository partsRepository = mock(PartsRepository.class);
    private Controller controller = new GetAllPartsController(partsRepository);

    @Test
    public void should_return_data_with_parts_response() {
        // setup
        when(partsRepository.getAll()).thenReturn(newArrayList(
                Part.builder().id(1).name("part-one").build(),
                Part.builder().id(2).name("part-two").build()
        ));

        // when
        Response response = controller.execute(mock(Request.class));

        // then
        assertEquals(Success.class, response.getClass());
        Object data = ((Success) response).getData();
        List<PartResponse> partResponses = (List) data;
        assertEquals(2, partResponses.size());
        assertPartResponse(partResponses.get(0), 1, "part-one");
        assertPartResponse(partResponses.get(1), 2, "part-two");
    }

    private void assertPartResponse(PartResponse partResponse, int expectedId, String expectedName) {
        assertEquals(expectedId, partResponse.getId());
        assertEquals(expectedName, partResponse.getName());
    }

}