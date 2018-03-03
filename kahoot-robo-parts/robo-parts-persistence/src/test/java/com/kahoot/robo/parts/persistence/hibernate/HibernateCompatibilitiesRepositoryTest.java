package com.kahoot.robo.parts.persistence.hibernate;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HibernateCompatibilitiesRepositoryTest extends HibernateTest {
    private HibernateCompatibilitiesRepository repository = new HibernateCompatibilitiesRepository(sessionFactory);

    @Test
    public void should_add_compatibility() {
        // when
        repository.addCompatibility(1, 2);
        // then
        assertThat(repository.getCompatibleParts(1)).containsExactlyInAnyOrder(2);
        assertThat(repository.getCompatibleParts(2)).containsExactlyInAnyOrder(1);

        // when
        repository.addCompatibility(1, 3);
        // then
        assertThat(repository.getCompatibleParts(1)).containsExactlyInAnyOrder(2, 3);
        assertThat(repository.getCompatibleParts(3)).containsExactlyInAnyOrder(1);

        // if I add the same compatibility again, nothing should change happen
        repository.addCompatibility(1, 3);
        assertThat(repository.getCompatibleParts(1)).containsExactlyInAnyOrder(2, 3);
        assertThat(repository.getCompatibleParts(3)).containsExactlyInAnyOrder(1);

        // when
        repository.addCompatibility(2, 3);
        // then
        assertThat(repository.getCompatibleParts(2)).containsExactlyInAnyOrder(1, 3);
        assertThat(repository.getCompatibleParts(3)).containsExactlyInAnyOrder(1, 2);

        // when
        repository.removeCompatibility(3, 1);
        // then
        assertThat(repository.getCompatibleParts(1)).containsExactlyInAnyOrder(2);
        assertThat(repository.getCompatibleParts(3)).containsExactlyInAnyOrder(2);
        assertThat(repository.getCompatibleParts(2)).containsExactlyInAnyOrder(1, 3);

        // when
        repository.removeAll(2);
        // then
        assertThat(repository.getCompatibleParts(2)).isEmpty();
    }
}