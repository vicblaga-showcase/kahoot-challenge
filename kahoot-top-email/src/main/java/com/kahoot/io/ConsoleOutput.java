package com.kahoot.io;

import com.kahoot.Output;

import java.io.PrintStream;
import java.util.Map;
import java.util.stream.Stream;

public class ConsoleOutput implements Output{
    private final int limit;
    private final PrintStream printStream;

    public ConsoleOutput() {
        this(10, System.out);
    }

    public ConsoleOutput(int limit, PrintStream printStream) {
        this.limit = limit;
        this.printStream = printStream;
    }

    @Override
    public void handle(Stream<Map.Entry<String, Long>> sortedCounts) {
        sortedCounts.limit(limit).forEach(printStream::println);
    }
}
