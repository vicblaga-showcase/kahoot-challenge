package com.kahoot;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Handles the output represented as sorted counts.
 */
public interface Output {
    void handle(Stream<Map.Entry<String,Long>> sortedCounts);
}
