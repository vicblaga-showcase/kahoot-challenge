package com.kahoot.robo.parts.core;

import java.util.List;
import java.util.Optional;

public interface PartsRepository {
    List<Part> getAll();

    Optional<Part> getById(int id);

    Part addPart(String name);

    Optional<Part> updatePart(int id, String name);

    Optional<Part> deleteById(int id);
}
