package com.kahoot.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import com.kahoot.InputChannel;

/**
 * Input channel that reads lines from the console (standard in).
 */
public class ConsoleInputChannel implements InputChannel {
    private final InputStream inputStream;

    public ConsoleInputChannel() {
        this(System.in);
    }

    public ConsoleInputChannel(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Collection<String> lines() throws IOException {
        System.out.println("Email addresses one per line (write 'end' to stop): ");
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while (!end(line = reader.readLine())) {
            lines.add(line);
        }

        return lines;
    }

    private boolean end(String line) {
        return line == null || line.equalsIgnoreCase("end") || line.isEmpty();
    }
}
