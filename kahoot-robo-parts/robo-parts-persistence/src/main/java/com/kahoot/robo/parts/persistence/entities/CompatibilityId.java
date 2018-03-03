package com.kahoot.robo.parts.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class CompatibilityId implements Serializable {
    private Integer fromId;
    private Integer toId;
}