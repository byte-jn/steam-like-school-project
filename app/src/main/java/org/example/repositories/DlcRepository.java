package org.example.repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.entities.Dlc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@Singleton
public class DlcRepository {

    private final SessionFactory sessionFactory;

    @Inject
    public DlcRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Dlc dlc) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(dlc);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public Optional<Dlc> findById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Dlc.class, id));
        }
    }

    public List<Dlc> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Dlc", Dlc.class).list();
        }
    }

    public List<Dlc> findByGameTitle(String gameTitle) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM Dlc WHERE lower(gameTitle) = lower(:gameTitle)", Dlc.class)
                    .setParameter("gameTitle", gameTitle)
                    .list();
        }
    }

    public void update(Dlc dlc) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(dlc);
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
            Dlc dlc = session.get(Dlc.class, id);
            if (dlc != null) {
                session.remove(dlc);
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
