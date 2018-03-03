package com.kahoot.robo.parts.persistence.hibernate;

import com.kahoot.robo.parts.core.Part;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.persistence.entities.PartEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HibernatePartsRepository implements PartsRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<Part> getAll() {
        Session session = sessionFactory.openSession();
        List<PartEntity> parts = session.createQuery("from Part").list();
        session.close();

        return parts.stream().map(this::toPart).collect(Collectors.toList());
    }

    @Override
    public Optional<Part> getById(int id) {
        Session session = sessionFactory.openSession();
        Optional<PartEntity> result = session.byId(PartEntity.class).loadOptional(id);
        session.close();

        return result.map(this::toPart);
    }

    @Override
    public Part addPart(String name) {
        PartEntity partEntity = PartEntity.builder().name(name).build();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(partEntity);
        session.getTransaction().commit();
        session.close();

        return toPart(partEntity);
    }

    @Override
    public Optional<Part> updatePart(int id, String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<PartEntity> partEntity = session.byId(PartEntity.class).loadOptional(id);
        partEntity.ifPresent(p -> {
            p.setName(name);
            session.save(p);
        });
        transaction.commit();
        session.close();

        return partEntity.map(this::toPart);
    }

    @Override
    public Optional<Part> deleteById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<PartEntity> partEntity = session.byId(PartEntity.class).loadOptional(id);
        partEntity.ifPresent(session::delete);
        transaction.commit();
        session.close();

        return partEntity.map(this::toPart);
    }

    private Part toPart(PartEntity partEntity) {
        return Part.builder().id(partEntity.getId()).name(partEntity.getName()).build();
    }
}
