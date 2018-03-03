package com.kahoot.io;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.kahoot.InputChannel;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

/**
 * Reads lines from a resource.
 */
public class ResourceInputChannel implements InputChannel {
    private final String resourceName;

    public ResourceInputChannel(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public Collection<String> lines() throws IOException {
        URL url = Resources.getResource(resourceName);
        return Resources.readLines(url, Charsets.UTF_8);
    }
}
