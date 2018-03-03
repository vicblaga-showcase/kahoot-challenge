package com.kahoot.robo.parts.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Compatibility")
@Table(name = "compatibilities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CompatibilityId.class)
public class CompatibilityEntity {
    @Id
    @Column(name = "fromId")
    private Integer fromId;

    @Id
    @Column(name = "toId")
    private Integer toId;
}
