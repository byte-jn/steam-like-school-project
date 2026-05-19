package org.example.di;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Factory
public class AppModule {

    /**
     * Provides the SessionFactory from EntityManager for Hibernate operations.
     */
    @Singleton
    public SessionFactory provideSessionFactory(EntityManager entityManager) {
        return entityManager.unwrap(Session.class).getSessionFactory();
    }
}
