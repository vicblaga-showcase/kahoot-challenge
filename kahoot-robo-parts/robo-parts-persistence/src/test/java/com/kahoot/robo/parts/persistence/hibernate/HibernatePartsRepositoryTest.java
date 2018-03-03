package com.kahoot.robo.parts.persistence.hibernate;

import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.persistence.entities.PartEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class HibernatePartsRepositoryTest extends HibernateTest {
    private HibernatePartsRepository repository = new HibernatePartsRepository(sessionFactory);

    @Test
    public void get_by_id_should_return_entity() {
        // setup

        PartEntity part1 = insertPart("part one");
        PartEntity part2 = insertPart("part two");

        // when
        Optional<Part> part = repository.getById(part1.getId());

        // then
        assertThat(part.isPresent()).isTrue();
        assertThat(part.get().getId()).isEqualTo(part1.getId());
        assertThat(part.get().getName()).isEqualTo("part one");

        part = repository.getById(part2.getId());
        assertThat(part.isPresent()).isTrue();
        assertThat(part.get().getId()).isEqualTo(part2.getId());
        assertThat(part.get().getName()).isEqualTo("part two");
    }

    @Test
    public void get_by_id_should_return_empty_if_entity_not_found() {
        // when
        Optional<Part> part = repository.getById(1);
        assertThat(part.isPresent()).isFalse();
    }

    @Test
    public void get_all_should_return_list_of_entities() {
        // setup
        String[] names = {"one", "two", "three"};
        for (String name : names) {
            insertPart(name);
        }

        // when
        List<Part> parts = repository.getAll();

        // then
        assertThat(parts).hasSize(3);
        List<String> partNames = parts.stream().map(Part::getName).collect(Collectors.toList());
        assertThat(partNames).contains(names);
    }

    @Test
    public void add_part_should_add_a_part() {
        // check to see if repository is empty
        assertThat(repository.getAll()).isEmpty();

        // add the part
        repository.addPart("test part");

        // get all parts again
        List<Part> parts = repository.getAll();

        // see if part with given name is present
        assertThat(parts).isNotEmpty();
        assertThat(parts.get(0).getName()).isEqualTo("test part");
    }

    @Test
    public void delete_part_should_work() {
        // setup
        int id = insertPart("test name").getId();

        // when
        Optional<Part> part = repository.deleteById(id);

        // then
        assertThat(part).isPresent();
        assertThat(part.map(Part::getName)).contains("test name");
        assertThat(repository.getAll()).isEmpty();

        // trying to delete same id should not work
        assertThat(repository.deleteById(id)).isNotPresent();
    }

    @Test
    public void update_part_should_work() {
        // setup
        int id = insertPart("test name").getId();

        // when
        Optional<Part> part = repository.updatePart(id, "new name");

        // then
        assertThat(part).isPresent();
        assertThat(part.map(Part::getName)).contains("new name");
        List<Part> parts = repository.getAll();
        assertThat(parts).isNotEmpty();
        assertThat(parts.get(0).getName()).isEqualTo("new name");
    }

    private PartEntity insertPart(String name) {
        Session session = sessionFactory.openSession();
        PartEntity partEntity = PartEntity.builder().name(name).build();
        Transaction t = session.beginTransaction();
        session.save(partEntity);
        t.commit();
        session.close();

        return partEntity;
    }

}