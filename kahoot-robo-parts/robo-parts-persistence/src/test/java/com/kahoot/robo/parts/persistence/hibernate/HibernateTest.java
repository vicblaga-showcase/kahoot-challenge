package com.kahoot.robo.parts.persistence.hibernate;
import com.kahoot.robo.parts.persistence.entities.CompatibilityEntity;
import com.kahoot.robo.parts.persistence.entities.PartEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class HibernateTest {
    protected static SessionFactory sessionFactory;

    @BeforeClass
    public static void setupSessionFactory() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(PartEntity.class)
                .addAnnotatedClass(CompatibilityEntity.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
                .setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
                .setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:test_db")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .buildSessionFactory();
    }

    @Before
    public void clearDatabase() {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.createNativeQuery("TRUNCATE SCHEMA public AND COMMIT").executeUpdate();
        t.commit();
        session.close();
    }

    @AfterClass
    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}
