package com.kahoot.robo.parts.containers;

import com.kahoot.robo.parts.core.CompatibilitiesRepository;
import com.kahoot.robo.parts.core.PartsRepository;
import com.kahoot.robo.parts.persistence.hibernate.HibernateCompatibilitiesRepository;
import com.kahoot.robo.parts.persistence.hibernate.HibernatePartsRepository;
import com.typesafe.config.Config;
import joni.dep.CustomContainer;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import static java.lang.String.format;

public class HibernateRepositoriesContainer extends CustomContainer {
    private final SessionFactory sessionFactory;

    public HibernateRepositoriesContainer(Config config) {
        super(config);
        String resourceName = getHibernateResourceName(config);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(resourceName)
                .build();
        try {
            this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException(e);
        }
    }

    public SessionFactory sessionFactory() {
        return sessionFactory;
    }

    public PartsRepository partsRepository() {
        return new HibernatePartsRepository(get(SessionFactory.class));
    }

    public CompatibilitiesRepository compatibilitiesRepository() {
        return new HibernateCompatibilitiesRepository(get(SessionFactory.class));
    }

    private String getHibernateResourceName(Config config) {
        if (config.hasPath("args.env")) {
            return format("hibernate-%s.cfg.xml", config.getString("args.env"));
        } else {
            return "hibernate.cfg.xml";
        }
    }
}
