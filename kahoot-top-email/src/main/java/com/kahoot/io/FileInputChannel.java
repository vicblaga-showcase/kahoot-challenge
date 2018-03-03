package com.kahoot.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import com.kahoot.InputChannel;

/**
 * Reads line from a file
 */
public class FileInputChannel implements InputChannel {
    private final String fileName;

    public FileInputChannel(String fileName) {
        this.fileName = fileName;    
    }
    
    @Override
    public Collection<String> lines() throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }
}
