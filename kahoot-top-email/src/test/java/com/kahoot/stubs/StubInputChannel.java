package com.kahoot.stubs;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.kahoot.InputChannel;

public class StubInputChannel implements InputChannel {
    private List<String> lines = Collections.emptyList();

    public void load(String... lines) {
        this.lines = Arrays.asList(lines);
    }

    @Override
    public Collection<String> lines() throws IOException {
        return lines;
    }
}
