package com.kahoot.io;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class ResourceInputChannelTest {
    private ResourceInputChannel resourceInputChannel;

    @Test
    public void lines_should_return_lines_from_resource_file() throws Exception {
        // setup
        resourceInputChannel = new ResourceInputChannel("resource.txt");

        // when
        Collection<String> lines = resourceInputChannel.lines();

        // then
        assertEquals(7, lines.size());
        assertThat(lines, containsInAnyOrder(
                "email1@test.org",
                "email2@test.org",
                "email3@home.com",
                "email4@hello.world",
                "a@b.c",
                "b@c.d",
                "d@e.f"
        ));
    }
}