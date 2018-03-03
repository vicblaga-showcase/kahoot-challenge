package com.kahoot.robo.parts.persistence.memory;

import com.kahoot.robo.parts.core.CompatibilitiesRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class MemoryCompatibilitiesRepository implements CompatibilitiesRepository {
    private final Map<Integer, Set<Integer>> compatibilities;

    @Override
    public void addCompatibility(int part1Id, int part2Id) {
        Set<Integer> currentCompatibilies = compatibilities.getOrDefault(part1Id, new HashSet<>());
        currentCompatibilies.add(part2Id);
        compatibilities.put(part1Id, currentCompatibilies);

        currentCompatibilies = compatibilities.getOrDefault(part2Id, new HashSet<>());
        currentCompatibilies.add(part1Id);
        compatibilities.put(part2Id, currentCompatibilies);
    }

    @Override
    public void removeCompatibility(int part1Id, int part2Id) {
        Set<Integer> currentCompatibilies = compatibilities.getOrDefault(part1Id, new HashSet<>());
        currentCompatibilies.remove(part2Id);
        compatibilities.put(part1Id, currentCompatibilies);

        currentCompatibilies = compatibilities.getOrDefault(part2Id, new HashSet<>());
        currentCompatibilies.remove(part1Id);
        compatibilities.put(part2Id, currentCompatibilies);
    }

    @Override
    public void removeAll(int id) {
        if (compatibilities.containsKey(id)) {
            Set<Integer> ids = compatibilities.remove(id);
            for (int otherId : ids) {
                Set<Integer> otherCompatibilitiesIds = compatibilities.getOrDefault(otherId, new HashSet<>());
                otherCompatibilitiesIds.remove(id);
                compatibilities.put(otherId, otherCompatibilitiesIds);
            }
        }
    }

    @Override
    public Collection<Integer> getCompatibleParts(int partId) {
        return compatibilities.getOrDefault(partId, new HashSet<>());
    }
}
