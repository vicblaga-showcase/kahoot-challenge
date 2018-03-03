package com.kahoot.robo.parts.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
public class Part {
    private int id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Part part = (Part) o;
        return id == part.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id);
    }
}
