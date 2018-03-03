package com.kahoot.robo.parts.core;

import java.util.Collection;

public interface CompatibilitiesRepository {
    void addCompatibility(int part1Id, int part2Id);
    void removeCompatibility(int part1Id, int part2Id);
    void removeAll(int id);
    Collection<Integer> getCompatibleParts(int partId);
}
