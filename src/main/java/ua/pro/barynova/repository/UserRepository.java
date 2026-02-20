package ua.pro.barynova.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.pro.barynova.entity.User;
import ua.pro.barynova.util.HibernateUtil;

public class UserRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    //CREATE
    public User save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            System.out.println("-> User saved: " + user.getUsername());
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error saving user: " + e.getMessage());
            return null;
        }
    }

    // READ by ID
    public User findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            System.out.println("xx Error finding user: " + e.getMessage());
            return null;
        }
    }

    // UPDATE
    public void update(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            System.out.println("-> User updated: " + user.getUsername());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error updating user: " + e.getMessage());
        }
    }

    // DELETE
    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                System.out.println("-> User deleted: " + user.getUsername());
            } else {
                System.out.println("x User not found");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error deleting user: " + e.getMessage());
        }
    }
}
