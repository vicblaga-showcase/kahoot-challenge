package com.kahoot.robo.parts.persistence.memory;

import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class MemoryPartsRepository implements PartsRepository {
    private final List<Part> parts;

    public MemoryPartsRepository() {
        this(new ArrayList<>());
    }

    @Override
    public List<Part> getAll() {
        return this.parts;
    }

    @Override
    public Optional<Part> getById(int id) {
        return parts.stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public Part addPart(String name) {
        Part part = Part.builder().id(nextId()).name(name).build();
        this.parts.add(part);

        return part;
    }

    @Override
    public Optional<Part> updatePart(int id, String name) {
        Optional<Part> part = getById(id);
        part.ifPresent(p -> p.setName(name));

        return part;
    }

    @Override
    public Optional<Part> deleteById(int id) {
        return Optional.empty();
    }

    private int nextId() {
        return parts.stream().mapToInt(Part::getId).max().orElse(-1) + 1;
    }
}
