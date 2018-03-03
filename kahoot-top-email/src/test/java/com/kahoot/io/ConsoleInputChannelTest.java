package com.kahoot.io;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

import com.google.common.io.Resources;
import org.junit.Test;

public class ConsoleInputChannelTest {
    private ConsoleInputChannel channel;

    @Test
    public void should_return_collection_of_lines() throws Exception {
        // setup
        InputStream inputStream = getResourceAsInputStream();

        // when
        channel = new ConsoleInputChannel(inputStream);
        Collection<String> lines = channel.lines();

        // then
        assertEquals(4, lines.size());
        assertThat(lines, containsInAnyOrder(
                "email1@test.org",
                "email2@test.org",
                "email3@home.com",
                "email4@hello.world"));
    }

    private InputStream getResourceAsInputStream() throws IOException {
        URL url = Resources.getResource("console_input.txt");
        return Resources.asByteSource(url).openStream();
    }
}
