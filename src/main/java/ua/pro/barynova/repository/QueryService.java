package ua.pro.barynova.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ua.pro.barynova.entity.User;
import ua.pro.barynova.util.HibernateUtil;
import java.util.List;

/**
 * QueryService - сервис для выполнения сложных HQL запросов
 * Здесь хранятся все запросы к БД через HQL
 */
public class QueryService {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    /**
     * Получить всех пользователей
     * @return список всех пользователей
     */
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT u FROM User u";
            Query<User> query = session.createQuery(hql, User.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("xx Error getting all users: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Получить пользователя по email
     * @param email - email для поиска
     * @return пользователь или null если не найден
     */
    public User getUserByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT u FROM User u WHERE u.email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            System.out.println("xx Error getting user by email: " + e.getMessage());
            return null;
        }
    }

    /**
     * Получить всех пользователей отсортированных по имени
     * @return список пользователей отсортированных по имени
     */
    public List<User> getAllUsersOrderedByName() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT u FROM User u ORDER BY u.username ASC";
            Query<User> query = session.createQuery(hql, User.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("xx Error getting users ordered by name: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Получить количество всех пользователей
     * @return количество пользователей в БД
     */
    public Long countAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(*) FROM User u";
            Query<Long> longQuery = session.createQuery(hql, Long.class);
            return longQuery.uniqueResult();
        } catch (Exception e) {
            System.out.println("xx Error counting users: " + e.getMessage());
            return 0L;
        }
    }
}
