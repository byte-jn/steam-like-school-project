package org.example.repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.entities.Game;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@Singleton
public class GameRepository {

    private final SessionFactory sessionFactory;

    @Inject
    public GameRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Game game) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(game);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public Optional<Game> findById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Game.class, id));
        }
    }

    public Optional<Game> findByTitle(String title) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM Game WHERE lower(title) = lower(:title)", Game.class)
                    .setParameter("title", title)
                    .uniqueResultOptional();
        }
    }

    public List<Game> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Game", Game.class).list();
        }
    }

    public void update(Game game) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(game);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void delete(String id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Game game = session.get(Game.class, id);
            if (game != null) {
                session.remove(game);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
