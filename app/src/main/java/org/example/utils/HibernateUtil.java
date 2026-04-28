package org.example.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() { }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration().configure();

        String host = System.getenv().getOrDefault("DB_HOST", "localhost");
        String port = System.getenv().getOrDefault("DB_PORT", "5432");
        String name = System.getenv().getOrDefault("DB_NAME", "app");
        String user = System.getenv().getOrDefault("DB_USER", "postgres");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "postgres");

        configuration.setProperty("hibernate.connection.url",
                "jdbc:postgresql://" + host + ":" + port + "/" + name);
        configuration.setProperty("hibernate.connection.username", user);
        configuration.setProperty("hibernate.connection.password", password);

        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        SESSION_FACTORY.close();
    }
}
