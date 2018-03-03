package com.kahoot.robo.parts.containers;

import com.google.common.collect.Lists;
import com.kahoot.robo.parts.core.CompatibilitiesRepository;
import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.persistence.memory.MemoryCompatibilitiesRepository;
import com.kahoot.robo.parts.persistence.memory.MemoryPartsRepository;
import com.typesafe.config.Config;
import joni.dep.CustomContainer;
import joni.dep.Qualifier;

import java.util.*;

public class MemoryRepositoriesContainer extends CustomContainer {
    private Part part1 = Part.builder().id(1).name("Left Arm Steel").build();
    private Part part2 = Part.builder().id(2).name("Right Arm Steel").build();
    private Part part3 = Part.builder().id(3).name("Chest Steel").build();
    private Part part4 = Part.builder().id(4).name("Head Steel").build();
    private Part part5 = Part.builder().id(5).name("Left Arm Wood").build();
    private Part part6 = Part.builder().id(6).name("Right Arm Wood").build();
    private Part part7 = Part.builder().id(7).name("Chest Wood").build();

    public MemoryRepositoriesContainer(Config config) {
        super(config);
    }

    @Qualifier("memory")
    public PartsRepository partsRepository() {
        return new MemoryPartsRepository(
                Lists.newArrayList(
                    part1, part2, part3, part4, part5, part6, part7
                )
        );
    }

    @Qualifier("memory")
    public CompatibilitiesRepository compatibilitiesRepository() {
        Map<Integer, Set<Integer>> compatibilities = new HashMap<>();
        compatibilities.put(1, new HashSet<>(Arrays.asList(2, 3, 4)));
        compatibilities.put(2, new HashSet<>(Arrays.asList(1, 3, 4)));
        compatibilities.put(3, new HashSet<>(Arrays.asList(1, 2, 5)));
        compatibilities.put(4, new HashSet<>(Arrays.asList(1, 2, 3)));
        compatibilities.put(5, new HashSet<>(Arrays.asList(6, 7)));
        compatibilities.put(6, new HashSet<>(Arrays.asList(5, 7)));
        compatibilities.put(7, new HashSet<>(Arrays.asList(5, 6)));

        return new MemoryCompatibilitiesRepository(compatibilities);
    }
}
