package com.kahoot.robo.parts.persistence.hibernate;

import com.kahoot.robo.parts.core.CompatibilitiesRepository;
import com.kahoot.robo.parts.persistence.entities.CompatibilityEntity;
import com.kahoot.robo.parts.persistence.entities.CompatibilityId;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RequiredArgsConstructor
public class HibernateCompatibilitiesRepository implements CompatibilitiesRepository {
    private final SessionFactory sessionFactory;

    @Override
    public void addCompatibility(int part1Id, int part2Id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CompatibilityEntity compatibilityEntity1 = session.byId(CompatibilityEntity.class)
                .loadOptional(id(part1Id, part2Id))
                .orElse(CompatibilityEntity.builder().fromId(part1Id).toId(part2Id).build());
        CompatibilityEntity compatibilityEntity2 = session.byId(CompatibilityEntity.class)
                .loadOptional(id(part2Id, part1Id))
                .orElse(CompatibilityEntity.builder().fromId(part2Id).toId(part1Id).build());

        session.save(compatibilityEntity1);
        session.save(compatibilityEntity2);
        transaction.commit();
        session.close();
    }

    @Override
    public void removeCompatibility(int part1Id, int part2Id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<CompatibilityEntity> compatibilityEntity1 = session.byId(CompatibilityEntity.class)
                .loadOptional(new CompatibilityId(part1Id, part2Id));
        Optional<CompatibilityEntity> compatibilityEntity2 = session.byId(CompatibilityEntity.class)
                .loadOptional(new CompatibilityId(part2Id, part1Id));
        compatibilityEntity1.ifPresent(session::delete);
        compatibilityEntity2.ifPresent(session::delete);
        transaction.commit();
        session.close();
    }

    @Override
    public void removeAll(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete from Compatibility where fromId=:id or toId=:id");
        query.setParameter("id", id);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public Collection<Integer> getCompatibleParts(int partId) {
        Set<Integer> result = new HashSet<>();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        result.addAll(compatibilityQuery(session, "fromId", partId, CompatibilityEntity::getToId));
        result.addAll(compatibilityQuery(session, "toId", partId, CompatibilityEntity::getFromId));
        transaction.commit();
        session.close();

        return result;
    }

    private Serializable id(int part1Id, int part2Id) {
        return new CompatibilityId(part1Id, part2Id);
    }

    private List<Integer> compatibilityQuery(Session session, String column, int value, Function<? super CompatibilityEntity, Integer> mapper) {
        String queryString = format("from Compatibility where %s=:value", column);
        Query<CompatibilityEntity> query = session.createQuery(queryString, CompatibilityEntity.class);
        query.setParameter("value", value);
        return query.list().stream().map(mapper).collect(Collectors.toList());
    }
}
