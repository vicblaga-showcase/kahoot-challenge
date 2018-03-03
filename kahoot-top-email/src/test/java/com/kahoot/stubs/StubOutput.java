package com.kahoot.stubs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.kahoot.Output;

public class StubOutput implements Output {
    private List<Map.Entry<String, Long>> sortedCounts;

    @Override
    public void handle(Stream<Map.Entry<String, Long>> sortedCounts) {
        this.sortedCounts = sortedCounts.collect(Collectors.toList());
    }

    public List<Map.Entry<String, Long>> getSortedCounts() {
        return sortedCounts;
    }
}
